package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MySessionUtil;
import com.example.zerobase.zerobaseminiassignment.model.ReportModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.ReportManageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/report")
public class ReportManageController {

    private static final Logger logger = LoggerFactory.getLogger(PostManageController.class);

    @Autowired
    private ReportManageService reportManageService;

    /**
     * 신고 등록 
     * @param reportModel
     * @param request
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody ReportModel reportModel, HttpServletRequest request){
        logger.info("[START] ReportManageController postCreate");

        Long nowMemberId = MySessionUtil.getSessionMemberId(request);

        ReportModel outputReport = reportManageService.save(reportModel, nowMemberId);

        if(outputReport == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:ReportModel",
                    outputReport
            );
        }

        logger.info("outputMember : [{}]",outputReport.toString());
        logger.info("[END] ReportManageController postCreate");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:ReportModel",
                outputReport
        );
    }
}
