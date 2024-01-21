package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.HashTagRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashTagManageService {

    @Autowired
    private HashTagRepository hashTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public HashTagModel save(String hashTag) {

        HashTagModel hashTagModel = this.findByTagNameEquals(hashTag);

        if(hashTagModel != null){
            hashTagModel.updateCount(hashTagModel.getCount()+1);
            hashTagModel = hashTagRepository.save(hashTagModel);
        }else{
            hashTagModel = hashTagRepository.save(new HashTagModel(hashTag.toLowerCase()));
        }

        return hashTagModel;
    }

    // 소문자로만 조회
    private HashTagModel findByTagNameEquals(String hashTag) {
        return hashTagRepository.findByTagNameEquals(hashTag.toLowerCase());
    }
}