package com.example.zerobase.zerobaseminiassignment.repository;

import com.example.zerobase.zerobaseminiassignment.model.PostHashTagModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHashTagRepository extends JpaRepository<PostHashTagModel,Long> {
}