package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.repository.HashTagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HashTagManageService {

    @Autowired
    private HashTagRepository hashTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public HashTagModel saveHashTagValidation(String hashTag) {
        log.info("HashTagManageService saveHashTagValidation");
        HashTagModel hashTagModel = this.find(hashTag);


        if(hashTagModel != null){
            hashTagModel = entityManager.find(HashTagModel.class, hashTagModel.getHashTagId());
            hashTagModel.updateCount(hashTagModel.getCount()+1);
            entityManager.merge(hashTagModel);
        }else{
            hashTagModel = hashTagRepository.save(new HashTagModel(hashTag.toLowerCase()));
            log.info("HashTagManageService count 1 ");
        }

        return hashTagModel;
    }

    // 소문자로만 조회
    public HashTagModel find(String hashTag) {

        HashTagModel hashTagModel = null;
        try {
            hashTagModel =  hashTagRepository.findByTagName(hashTag.toLowerCase());
        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
        }

        return hashTagModel;
    }

    public List<HashTagModel> findAll() {

        List<HashTagModel> hashTagModelList = hashTagRepository.findAll();

        return hashTagModelList;
    }

    public boolean delete(Long hashTagId) {

        try{
            hashTagRepository.deleteById(hashTagId);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}