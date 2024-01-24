package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyDateUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.*;
import com.example.zerobase.zerobaseminiassignment.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class PostManageService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberManageService memberManageService;

    // 순환참조 -> 확인
    // @Autowired
    // private BlockManageService blockManageService;

    @Autowired
    private HashTagManageService hashTagManageService;
    @Autowired
    private PostHashTagManageService postHashTagManageService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     * @param postModel
     * @return
     */
    // hashTag 동시 저장.
    public PostModel save(PostModel postModel) {
        log.info("PostManageService save");
        postModel.setMemberId(MyJwtUtil.getMember());
        postModel.setRegistrationDate(MyDateUtil.getData().getRegistrationDate());
        PostModel outputPost = null;
        try {

            outputPost = postRepository.save(postModel);

            // [save] hashTag
            List<String> hashTags = extractHashTags(outputPost.getContents());

            for(String hashTag : hashTags){
                log.info("hashTag [{}]",hashTag);
                HashTagModel hashTagModel = hashTagManageService.save(hashTag);
                log.info("HashTagModel [{}]",hashTagModel.toString());
                // [save] post_hashTag
                postHashTagManageService.save(new PostHashTagModel(outputPost,hashTagModel,hashTag));
            }

        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
        }

        return outputPost;
    }

    // 기존에 find 로 접속 불가 findAll 통해서만 가능
    public PostModel find(Long id){
        PostModel result = null;
        Optional<PostModel> getPost = postRepository.findById(id);
        if(getPost.isPresent()) {
            result = getPost.get();
        };

        return result;
    }

    public List<PostModel> findAll(){
        List<PostModel> allPost = postRepository.findAll();

        // 순환참조 해제를 위한 개별 생성. 확인이 필요하다.
        BlockManageService blockManageService = new BlockManageService();
        List<BlockModel> allBlock = blockManageService.findAll();

        if(allBlock == null){
            throw new RuntimeException("block is null");
        }

        //userList.removeIf(user -> "A".equals(user.getName()));
        // 해당 부분 중 특정 값만 삭제 후 반환
        for(BlockModel block : allBlock){
            if(block.getBlockMember() != null){
                allPost.removeIf(post -> block.getBlockMember().getMemberId().equals(post.getMemberId().getMemberId()));
            }else if (block.getBlockPost() != null){
                allPost.removeIf(post -> block.getBlockPost().getPostId().equals(post.getPostId()));
            }
        }

        return allPost;
    }

    public List<PostModel> findByTitle(String title){

        List<PostModel> postModelList = postRepository.findByTitle(title);

        return postModelList;
    }

    // hashtag 추출
    private List<String> extractHashTags(String text) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            hashtags.add(matcher.group());
        }

        return hashtags;
    }

    @Transactional
    public PostModel update(Long postId, PostModel postModel) {
        log.info("PostManageService update");
        Optional<PostModel> opExistData = postRepository.findById(postId);
        PostModel existingData = null;
        if(opExistData.isPresent() &&
                (MyJwtUtil.checkAuth(MyAuthUtil.MANAGER)
                        ||
                        opExistData.get().getMemberId().getMemberId().equals(MyJwtUtil.getMemberId()))
        ){
            existingData = opExistData.get();
        }else {
            log.error("exist data is null");
            return existingData;
        }
        if(StringUtils.hasText(postModel.getContents())){
            existingData.setContents(postModel.getContents());
        }
        if(StringUtils.hasText(postModel.getTitle())){
            existingData.setTitle(postModel.getTitle());
        }
        if(StringUtils.hasText(postModel.getStatus())){
            existingData.setStatus(postModel.getStatus());
        }

        existingData = entityManager.merge(existingData);

        return existingData;
    }

    /**
     * 게시글 삭제
     * ✅ 삭제에 대한 권한 체크
     * ✅ 타 참조 테이블 확인해 같이 넣어 주기 : 신고x, 차단0, 게시글0
     * @return
     */
    public boolean delete(Long postId) {
        if(!MyJwtUtil.checkAuth(MyAuthUtil.MANAGER)){
            log.error("need Authority");
            return false;
        }

        try{
            Optional<PostModel> postModel = postRepository.findById(postId);
            // ㅜ건한 체크
            if(postModel.isPresent() &&
                    postModel.get().getMemberId().getMemberId().equals(MyJwtUtil.getMemberId())
            ){
                postHashTagManageService.deleteByPostId(postModel.get());
                postRepository.deleteById(postId);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 참조 삭제에 대해서는 권한 체크를 하지 않는다.
     * @param memberModel
     * @return
     */
    public boolean deleteByMemberId(MemberModel memberModel) {
        try{
            postRepository.deleteByMemberId(memberModel);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
