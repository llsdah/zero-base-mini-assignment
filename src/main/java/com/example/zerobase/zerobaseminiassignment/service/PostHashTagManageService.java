package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.PostHashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.repository.PostHashTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostHashTagManageService {

    @Autowired
    private PostHashTagRepository postHashTagRepository;

    public PostHashTagModel save(PostHashTagModel postHashTagModel){
       return  postHashTagRepository.save(postHashTagModel);
    }

    public boolean deleteByPostId(PostModel postModel) {
        try{
            postHashTagRepository.deleteByPostId(postModel);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
