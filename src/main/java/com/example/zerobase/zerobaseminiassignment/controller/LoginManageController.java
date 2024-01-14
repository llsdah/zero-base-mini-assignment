package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MemberUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/site")
public class LoginManageController {

    private static final Logger logger = LoggerFactory.getLogger(LoginManageController.class);


    @PostMapping("/login")
    @ResponseBody
    public ResultMessageModel postLogin(@RequestBody Long id, HttpServletRequest request) {
        logger.info("[START] LoginManageController postCreate");

        HttpSession session = request.getSession();

        session.setMaxInactiveInterval(600);
        session.setAttribute("memberID",id);
        logger.info("session memberID create [{}]",session.getAttribute("memberID"));

        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:Sesstion Create"
        );

    }
}