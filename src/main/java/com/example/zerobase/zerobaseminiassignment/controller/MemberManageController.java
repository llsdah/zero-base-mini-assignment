package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MemberUtil;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.GroupManageService;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 회원 생성기능 + "매니저" 일경우 세션에 추가
 */
@Controller
@RequestMapping("/member")
public class MemberManageController {

    private static final Logger logger = LoggerFactory.getLogger(MemberManageController.class);

    @Autowired
    private MemberManageService memberManageService;

    @PostMapping("/create")
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody MemberModel memberModel, HttpServletRequest request){
        logger.info("[START] GroupManageController postInvite");

        HttpSession session = request.getSession();

        if(memberModel.getAuthority().equals(MemberUtil.MANAGER)){
            session.setMaxInactiveInterval(10);
            session.setAttribute(MemberUtil.MANAGER,true);
            logger.info("session create [{}]",session.getAttribute(MemberUtil.MANAGER));
        }

        MemberModel outputMember = memberManageService.create(memberModel);

        if(outputMember == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:MemberModel"
            );
        }

        logger.info("outputMember : [{}]",outputMember.toString());
        logger.info("[END] GroupManageController postInvite");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postCreate|"+(
                        session.getAttribute(MemberUtil.MANAGER) != null
                )
        );
    }
}
