package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.LikePostModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.repository.LikePostRepository;
import com.example.zerobase.zerobaseminiassignment.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LikePostMenageService {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private PostRepository postRepository;

    public boolean save(Long postId) {
        try{
            Optional<PostModel> postModel = postRepository.findById(postId);
            likePostRepository.save(new LikePostModel(postModel.get(), MyJwtUtil.getMember()));

        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(Long postId) {
        try{
            Optional<PostModel> postModel = postRepository.findById(postId);
            likePostRepository.deleteByLikedPostAndLikingMember(postModel.get(), MyJwtUtil.getMember());

        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}
