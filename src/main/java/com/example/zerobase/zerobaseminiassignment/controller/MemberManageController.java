package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.FollowManageService;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 회원 생성기능 + "매니저" 일경우 세션에 추가
 */
@Slf4j
@Controller
@RequestMapping("/members")
public class MemberManageController {

    @Autowired
    private MemberManageService memberManageService;

    @Autowired
    private FollowManageService followManageService;

    /**
     * 맴버 추가
     * @param memberModel
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody MemberModel memberModel) {
        log.info("[START] MemberManageController postCreate");

        MemberModel result = memberManageService.save(memberModel);

        log.info("[END] MemberManageController postCreate");

        if(result != null){
            return ResultMessageUtil.success("postCreate", result);
        }
        return ResultMessageUtil.fail();

    }

    /**
     * 맴버 조회, 본인조회시에도 데이터 전달
     * @return
     */
    @GetMapping("/{memberId}")
    @ResponseBody
    public ResultMessageModel getFind(@PathVariable("memberId") Long memberId) {
        log.info("[START] MemberManageController getFind : "+memberId);

        MemberModel result = memberManageService.find(memberId);

        log.info("[END] MemberManageController getFind : "+memberId);

        if(result != null){
            return ResultMessageUtil.success("getFind", result);
        }

        return ResultMessageUtil.fail();

    }


    /**
     * 전체 조회
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getFindAll() {
        log.info("[START] MemberManageController getFindAll");

        List<MemberModel> result = memberManageService.findAll();

        log.info("[END] MemberManageController getFindAll");

        if(result != null){
            return ResultMessageUtil.success("getFindAll", result);
        }
        return ResultMessageUtil.fail();
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

        MemberModel result = memberManageService.update(memberId, memberModel);

        log.info("[END] MemberManageController updateMember");
        if(result != null){
            return ResultMessageUtil.success("updateMember", result);
        }

        return ResultMessageUtil.fail();
    }
    /**
     * 회원 데이터 삭제.
     * @return
     */
    @DeleteMapping("/{memberId}")
    @ResponseBody
    public ResultMessageModel deleteMember(@PathVariable("memberId")Long memberId){
        log.info("[START] MemberManageController deleteMember");

        boolean result = memberManageService.delete(memberId);

        log.info("[END] MemberManageController deleteMember");
        if(result){
            return ResultMessageUtil.success("postCreate", result);
        }
        return ResultMessageUtil.fail();
    }

    /**
     * 팔로워 추가
     * @param memberId
     * @return
     */
    @PostMapping("/follow/{memberId}")
    @ResponseBody
    public ResultMessageModel postFollow(@PathVariable("memberId") Long memberId) {
        log.info("[START] MemberManageController postFollow");

        boolean result = followManageService.save(memberId);

        log.info("[END] MemberManageController postFollow");
        if(result){
            return ResultMessageUtil.success("postFollow", result);
        }
        return ResultMessageUtil.fail();
    }

    /**
     * 팔로워 삭제
     * @param memberId
     * @return
     */
    @DeleteMapping("/follow/{memberId}")
    @ResponseBody
    public ResultMessageModel deleteFollow(@PathVariable("memberId") Long memberId) {
        log.info("[START] MemberManageController deleteFollow");

        boolean result = followManageService.delete(memberId);

        log.info("[END] MemberManageController deleteFollow");
        if(result){
            return ResultMessageUtil.success("deleteFollow", result);
        }
        return ResultMessageUtil.fail();
    }
}