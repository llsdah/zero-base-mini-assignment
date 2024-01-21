package com.example.zerobase.zerobaseminiassignment.repository;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberModel, Long> {
    Optional<MemberModel> findByUsername(String username);
    Optional<MemberModel> findByEmailAndPassword(String email, String password);
}
