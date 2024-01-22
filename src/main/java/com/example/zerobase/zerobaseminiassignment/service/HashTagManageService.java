package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
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
    public ResultMessageModel save(String hashTag) {
        log.info("HashTagManageService save");
        HashTagModel hashTagModel = null;
        if(this.find(hashTag).getData() instanceof HashTagModel){
            hashTagModel = (HashTagModel) this.find(hashTag).getData();
        }

        if(hashTagModel != null){
            hashTagModel = entityManager.find(HashTagModel.class, hashTagModel.getHashTagId());
            hashTagModel.updateCount(hashTagModel.getCount()+1);
            entityManager.merge(hashTagModel);
        }else{
            hashTagModel = hashTagRepository.save(new HashTagModel(hashTag.toLowerCase()));
            log.info("HashTagManageService count 1 ");
        }

        return ResultMessageUtil.success(hashTagModel);
    }

    // 소문자로만 조회
    public ResultMessageModel find(String hashTag) {

        HashTagModel hashTagModel;
        try {
            hashTagModel =  hashTagRepository.findByTagNameEquals(hashTag.toLowerCase());
        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
            return ResultMessageUtil.fail();
        }

        return ResultMessageUtil.success(hashTagModel);
    }

    public ResultMessageModel findAll() {

        List<HashTagModel> hashTagModelList = hashTagRepository.findAll();
        if(hashTagModelList.isEmpty()){
            return ResultMessageUtil.fail();
        }

        return ResultMessageUtil.success(hashTagModelList);
    }
}