package com.example.zerobase.zerobaseminiassignment.repository;

import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<BlockModel,Long> {
    List<BlockModel> findBlockModelByBlockerMember(MemberModel blockerMember);
}
