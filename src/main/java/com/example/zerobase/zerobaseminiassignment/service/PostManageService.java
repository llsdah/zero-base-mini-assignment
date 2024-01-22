package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyDateUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.*;
import com.example.zerobase.zerobaseminiassignment.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    /**
     *
     * @param postModel
     * @return
     */
    // hashTag 동시 저장.
    public ResultMessageModel save(PostModel postModel) {
        log.info("PostManageService save");
        log.info("jet auth test");
        MyJwtUtil.checkAuth();
        log.info("jet auth test");
        postModel.setMemberId(MyJwtUtil.getMember());
        postModel.setRegistrationDate(MyDateUtil.getData().getRegistrationDate());

        try {

            PostModel outputPost = postRepository.save(postModel);

            // [save] hashTag
            List<String> hashTags = extractHashTags(outputPost.getContents());

            for(String hashTag : hashTags){
                log.info("hashTag [{}]",hashTag);
                HashTagModel hashTagModel = (HashTagModel) hashTagManageService.save(hashTag).getData();
                log.info("HashTagModel [{}]",hashTagModel.toString());
                // [save] post_hashTag
                postHashTagManageService.save(new PostHashTagModel(outputPost,hashTagModel,hashTag));
            }

        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
            return ResultMessageUtil.fail();
        }

        return ResultMessageUtil.success();
    }

    // 기존에 find 로 접속 불가 findAll 통해서만 가능
    public ResultMessageModel find(Long id){

        Optional<PostModel> getPost = postRepository.findById(id);
        if(getPost.isPresent()) {
            return ResultMessageUtil.success(getPost.get());
        };

        return ResultMessageUtil.fail();
    }

    public ResultMessageModel findAll(){
        List<PostModel> allPost = postRepository.findAll();

        // 순환참조 해제를 위한 개별 생성. 확인이 필요하다.
        BlockManageService blockManageService = new BlockManageService();
        List<BlockModel> allBlock = (List<BlockModel>) blockManageService.findAll().getData();

        //userList.removeIf(user -> "A".equals(user.getName()));
        // 해당 부분 중 특정 값만 삭제 후 반환
        for(BlockModel block : allBlock){
            if(block.getBlockMember() != null){
                allPost.removeIf(post -> block.getBlockMember().getMemberId().equals(post.getMemberId().getMemberId()));
            }else if (block.getBlockPost() != null){
                allPost.removeIf(post -> block.getBlockPost().getPostId().equals(post.getPostId()));
            }
        }

        if(allPost.isEmpty()){
            return ResultMessageUtil.fail();
        }

        return ResultMessageUtil.success(allPost);
    }

    public List<PostModel> findByTitle(String title){

        return postRepository.findByTitle(title);
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

}
