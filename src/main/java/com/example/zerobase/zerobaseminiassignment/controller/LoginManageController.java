package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtProvider;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/site")
public class LoginManageController {

    @Autowired
    private MemberManageService memberManageService;

    /**
     * 간단 로그인 향후 업데이트 필요
     * @param memberModel
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultMessageModel postLogin(@RequestBody MemberModel memberModel) {
        log.info("[START] LoginManageController postLogin");

        ResultMessageModel result = memberManageService.checkLoginMember(memberModel);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:postLogin");
        }else {
            result.setMessageContent("[FAIL]:postLogin");
        }

        log.info("[END] LoginManageController postLogin");
        return result;

    }
}