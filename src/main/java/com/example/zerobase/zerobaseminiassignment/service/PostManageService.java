package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthorityUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.*;
import com.example.zerobase.zerobaseminiassignment.repository.*;
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
    private MemberRepository memberRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private HashTagManageService hashTagManageService;
    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private PostHashTagRepository postHashTagRepository;

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
        PostModel outputPost = null;
        try {

            outputPost = postRepository.save(postModel);

            // [save] hashTag
            List<String> hashTags = extractHashTags(outputPost.getContents());

            for(String hashTag : hashTags){
                log.info("hashTag [{}]",hashTag);
                HashTagModel hashTagModel = hashTagManageService.saveHashTagValidation(hashTag.toLowerCase());

                // [save] post_hashTag
                postHashTagRepository.save(new PostHashTagModel(outputPost,hashTagModel,hashTag));
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

        if(MyJwtUtil.checkAuth("Manager")){
            return allPost;
        }

        allPost = deleteBlockPost(allPost);
        return allPost;
    }

    public List<PostModel> getPostFilterByTitle(String title){

        List<PostModel> postModelList = postRepository.findByTitle(title);

        return postModelList;
    }

    // hashtag 추출
    private List<String> extractHashTags(String text) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            log.info("matcher.find() : "+matcher.group());
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
                (MyJwtUtil.checkAuth(MyAuthorityUtil.MANAGER)
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
        if(!MyJwtUtil.checkAuth(MyAuthorityUtil.MANAGER)){
            log.error("need Authority");
            return false;
        }

        try{
            Optional<PostModel> postModel = postRepository.findById(postId);
            // ㅜ건한 체크
            if(postModel.isPresent() &&
                    postModel.get().getMemberId().getMemberId().equals(MyJwtUtil.getMemberId())
            ){
                postHashTagRepository.deleteByPostId(postModel.get());
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

    public List<PostModel> getPostFilterByHashTag(String hashTags) {
        // 해시 태그라고 저장된 값에는 # 이 붙어 있다. 다만 조회 할떄는 같이 넘겨 준다고 예상이 된다. 그렇기에 굳이 필터 하기 않겠다.
        // 태크와 매칭된 태그 번호를 가져온 후 PostHashTag를 조회해 반환해 준다.
        HashTagModel hashTagModel = hashTagRepository.findByTagName(hashTags.toLowerCase());

        List<PostHashTagModel> filterByPostHashTagModelList = postHashTagRepository.findByHashTagId(hashTagModel);
        List<PostModel> postModelList = new ArrayList<>();

        for(PostHashTagModel item : filterByPostHashTagModelList){
            postModelList.add(item.getPostId());
        }

        postModelList = deleteBlockPost(postModelList);

        return postModelList;
    }

    /**
     * 차단 게시글 조회 제외 로직
     */
    private List<PostModel> deleteBlockPost(List<PostModel> postModelList){

        // 차단된 회원이 있는 경우 없에겠습니다
        List<BlockModel> blockModelList = blockRepository.findBlockModelByBlockerMember(MyJwtUtil.getMember());

        for(BlockModel blockModel : blockModelList ){
            // 차단 게시글 삭제
            if(blockModel.getBlockPost()!=null){
                postModelList.remove(blockModel.getBlockPost());
                continue;
            }
            // 차단 맴버의 게시글 삭제
            MemberModel block = blockModel.getBlockMember();

            postModelList.removeIf(postModel -> postModel.getMemberId().equals(block));
        }

        return postModelList;
    }
}
