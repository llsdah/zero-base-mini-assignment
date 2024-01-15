package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.PostHashTagModel;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import com.example.zerobase.zerobaseminiassignment.repository.PostHashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostHashTagManageService {

    @Autowired
    private PostHashTagRepository hashTagRepository ;

    public PostHashTagModel save(PostHashTagModel postHashTagModel){
       return  hashTagRepository.save(postHashTagModel);
    }
}
