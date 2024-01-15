package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyDateUtil;
import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.PostHashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostManageService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberManageService memberManageService;
    @Autowired
    private BlockManageService blockManageService;
    @Autowired
    private HashTagManageService hashTagManageService;
    @Autowired
    private PostHashTagManageService postHashTagManageService;

    // hashTag 동시 저장.
    public PostModel save(PostModel postModel, Long nowMemberId) {
        postModel.setMemberId(memberManageService.find(1L));
        postModel.setRegistrationDate(MyDateUtil.getData().getRegistrationDate());
        postModel.setMemberId(memberManageService.find(nowMemberId));
        PostModel outputPost = postRepository.save(postModel);

        // [save] hashTag
        List<String> hashTags = extractHashTags(outputPost.getContents());

        for(String hashTag : hashTags){
            HashTagModel hashTagModel = hashTagManageService.save(hashTag);
            // [save] post_hashTag
            postHashTagManageService.save(new PostHashTagModel(outputPost,hashTagModel,hashTag));
        }

        return outputPost;
    }

    // 기존에 find 로 접속 불가 findAll 통해서만 가능
    public PostModel find(Long id){
        boolean flag = postRepository.findById(id).isPresent();
        if(!flag) return null;

        return postRepository.findById(id).get();
    }

    public List<PostModel> findAll(Long nowMemberId){

        List<PostModel> allPost = postRepository.findAll();
        List<BlockModel> allBlock = blockManageService.findAll(nowMemberId);
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
            return null;
        }
        return allPost;
    }

    public List<PostModel> findByTitle(String title){

        return postRepository.findByTitle(title);
    }

    // hashtag 추출
    private static List<String> extractHashTags(String text) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            hashtags.add(matcher.group());
        }

        return hashtags;
    }
}
