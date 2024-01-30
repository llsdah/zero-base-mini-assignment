package com.example.zerobase.zerobaseminiassignment.repository;

import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagRepository extends JpaRepository<HashTagModel,Long>{

    HashTagModel findByTagName(String tagName);
}

