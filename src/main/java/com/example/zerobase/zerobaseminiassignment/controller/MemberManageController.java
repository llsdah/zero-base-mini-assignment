package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 회원 생성기능 + "매니저" 일경우 세션에 추가
 */
@Slf4j
@Controller
@RequestMapping("/members")
public class MemberManageController {

    @Autowired
    private MemberManageService memberManageService;

    /**
     * 맴버 추가
     * @param memberModel
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody MemberModel memberModel) {
        log.info("[START] MemberManageController postCreate");

        ResultMessageModel result = memberManageService.save(memberModel);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:postCreate");
        }else {
            result.setMessageContent("[FAIL]:postCreate");
        }

        log.info("[END] MemberManageController postCreate");
        return result;
    }

    /**
     * 맴버 조회, 본인조회시에도 데이터 전달
     * @return
     */
    @GetMapping("/{memberId}")
    @ResponseBody
    public ResultMessageModel getFind(@PathVariable("memberId") Long memberId) {
        log.info("[START] MemberManageController getFind : "+memberId);

        ResultMessageModel result = memberManageService.find(memberId);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:getFind");
        }else {
            result.setMessageContent("[FAIL]:getFind");
        }

        log.info("[END] MemberManageController getFind : "+memberId);
        return result;
    }


    /**
     * 전체 조회
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getFindAll() {
        log.info("[START] MemberManageController getFindAll");

        ResultMessageModel result = memberManageService.findAll();

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:getFind");
        }else {
            result.setMessageContent("[FAIL]:getFind");
        }

        log.info("[END] MemberManageController getFindAll");
        return result;
    }

    /**
     * 회원 데이터 수정.
     * @param memberModel
     * @return
     */
    @PutMapping("/{memberId}")
    @ResponseBody
    public ResultMessageModel updateMember(@PathVariable("memberId")Long memberId, MemberModel memberModel){
        log.info("[START] MemberManageController updateMember");
        log.info("[START] MemberManageController updateMember : "+memberId);

        ResultMessageModel result = memberManageService.update(memberId, memberModel);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:updateMember");
        }else {
            result.setMessageContent("[FAIL]:updateMember");
        }

        log.info("[END] MemberManageController updateMember");
        return result;
    }
    /**
     * 회원 데이터 수정.
     * @return
     */
    @DeleteMapping("/{memberId}")
    @ResponseBody
    public ResultMessageModel deleteMember(@PathVariable("memberId")Long memberId){
        log.info("[START] MemberManageController deleteMember");
        ResultMessageModel result = memberManageService.delete(memberId);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:deleteMember");
        }else {
            result.setMessageContent("[FAIL]:deleteMember");
        }

        log.info("[END] MemberManageController deleteMember");
        return result;
    }


}