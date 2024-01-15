package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.ReportModel;
import com.example.zerobase.zerobaseminiassignment.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportManageService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private MemberManageService memberManageService;

    public ReportModel save(ReportModel reportModel, Long nowMemberId) {
        reportModel.setReporterMemberId(memberManageService.find(nowMemberId));
        return reportRepository.save(reportModel);
    }
}
