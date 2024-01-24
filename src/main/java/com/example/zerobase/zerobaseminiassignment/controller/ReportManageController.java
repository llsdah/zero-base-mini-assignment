package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MySessionUtil;
import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.ReportModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.ReportManageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/reports")
public class ReportManageController {

    @Autowired
    private ReportManageService reportManageService;

    /**
     * 신고 등록 
     * @param reportModel
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody ReportModel reportModel){
        log.info("[START] ReportManageController postCreate");

        ReportModel result = reportManageService.save(reportModel);
        log.info("[END] ReportManageController postCreate");

        if(result != null){
            return ResultMessageUtil.success("postCreate", result);
        }

        return ResultMessageUtil.fail();
    }

    /**
     * 본인이 신고한 부분 조회 / 관리자일 경우 전체 조회
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getReports(){
        log.info("[START] ReportManageController getReports");

        List<ReportModel> result = reportManageService.findUserReport() ;

        log.info("[END] ReportManageController getReports");
        if(result != null){
            return ResultMessageUtil.success("getReports", result);
        }

        return ResultMessageUtil.fail();
    }

    @PutMapping("/{reportId}")
    @ResponseBody
    public ResultMessageModel updateReport(@PathVariable("reportId")Long reportId, ReportModel reportModel){
        log.info("[START] ReportManageController updateReport");
        ReportModel result = reportManageService.update(reportId, reportModel);

        log.info("[END] ReportManageController updateReport");
        if(result != null){
            return ResultMessageUtil.success("updateReport", result);
        }

        return ResultMessageUtil.fail();
    }

    /**
     * 신고 데이터 삭제.
     * @return
     */
    @DeleteMapping("/{reportId}")
    @ResponseBody
    public ResultMessageModel deleteReport(@PathVariable("reportId")Long reportId){
        log.info("[START] ReportManageController deleteMember");
        boolean result = reportManageService.delete(reportId);

        log.info("[END] ReportManageController deleteMember");
        if(result){
            return ResultMessageUtil.success("deleteReport", result);
        }

        return ResultMessageUtil.fail();
    }
    
}
