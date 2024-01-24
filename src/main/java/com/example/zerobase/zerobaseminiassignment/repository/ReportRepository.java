package com.example.zerobase.zerobaseminiassignment.repository;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportModel,Long> {
    List<ReportModel> findByReporterMemberId(MemberModel reporterMemberId);
    void deleteByTargetMemberId(MemberModel memberModel);
}
