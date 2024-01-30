package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import com.example.zerobase.zerobaseminiassignment.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api")
public class LoginManageController {

    @Autowired
    private MemberManageService memberManageService;

    @Autowired
    private RedisService redisService;
    /**
     * 간단 로그인 향후 업데이트 필요
     * @param memberModel
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultMessageModel postLogin(@RequestBody MemberModel memberModel) {
        log.info("[START] LoginManageController postLogin");

        String result = memberManageService.checkLoginMember(memberModel);
        return ResultMessageUtil.resultMessage("S0001","로그인에 성공하셨습니다.",result);
    }

    /**
     * 로그아웃 기능
     * @param token
     * @return
     */
    @GetMapping("/logout")
    @ResponseBody
    public ResultMessageModel getLogout(@RequestHeader("Authorization") String token) {
        log.info("[START] LoginManageController postLogout");
        token = token.substring(7);
        redisService.addBlackList(token);
        log.info("redis check token : "+ redisService.isBlackList(token));

        return ResultMessageUtil.resultMessage("S0002","성공적으로 로그아웃 하셨습니다.","");

    }

}