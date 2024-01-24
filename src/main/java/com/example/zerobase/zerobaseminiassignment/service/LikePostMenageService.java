package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.LikePostModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.repository.LikePostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LikePostMenageService {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private PostManageService postManageService;

    public boolean save(Long postId) {
        try{
            PostModel postModel = postManageService.find(postId);
            likePostRepository.save(new LikePostModel(postModel, MyJwtUtil.getMember()));

        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(Long postId) {
        try{
            PostModel postModel = postManageService.find(postId);
            likePostRepository.deleteByLikedPostAndLikingMember(postModel, MyJwtUtil.getMember());

        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}
