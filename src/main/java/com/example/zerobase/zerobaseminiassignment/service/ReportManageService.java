package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthorityUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ReportModel;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import com.example.zerobase.zerobaseminiassignment.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReportManageService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ReportModel save(ReportModel reportModel) {
        Optional<MemberModel> memberModel = memberRepository.findById(MyJwtUtil.getMemberId());
        if(memberModel.isEmpty()) return null;
        reportModel.setReporterMemberId(memberModel.get());

        return reportRepository.save(reportModel);
    }

    @Transactional
    public ReportModel update(Long reportId,ReportModel reportModel) {
        log.info("ReportManageService update");
        ReportModel existingData = null;
        try {
            existingData = entityManager.find(ReportModel.class, reportId);;

            if(existingData.getStatus()!=0){
                throw new Exception("already been completed report");
            }

            // 기존 데이터데 수정된 데이터가 있으면 붙영 넣고 다시 저장.
            if(!StringUtils.hasText(existingData.getAnswer())){
                existingData.setAnswer(reportModel.getAnswer());
            }

            entityManager.merge(existingData);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return existingData;

    }
    public boolean delete(Long reportId) {

        try{
            reportRepository.deleteById(reportId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    public List<ReportModel> findUserReport() {
        //관리자일 경우 전체 조회가 가능하도록
        List<ReportModel> reportModelList;

        if(MyJwtUtil.checkAuth(MyAuthorityUtil.MANAGER)){
            reportModelList = reportRepository.findAll();
        }else {
            reportModelList = reportRepository.findByReporterMemberId(MyJwtUtil.getMember());
        }

        return reportModelList;
    };

    public boolean deleteByTargetMemberId(MemberModel memberModel) {
        try{
            reportRepository.deleteByTargetMemberId(memberModel);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;

    }
}
