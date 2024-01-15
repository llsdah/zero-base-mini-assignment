package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.HashTagRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashTagManageService {

    @Autowired
    private HashTagRepository hashTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public HashTagModel save(String hashTag) {

        HashTagModel hashTagModel = this.findByTagNameEquals(hashTag);

        if(hashTagModel != null){
            hashTagModel.setCount(hashTagModel.getCount()+1);
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
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