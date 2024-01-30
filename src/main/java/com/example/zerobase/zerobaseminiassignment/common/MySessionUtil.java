package com.example.zerobase.zerobaseminiassignment.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySessionUtil {

    public static Long getSessionMemberId(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (Long) session.getAttribute("memberID");
    }
    public static Long setSessionMemberId(HttpServletRequest request){
        HttpSession session = request.getSession();

        session.setMaxInactiveInterval(10);
        session.setAttribute(MyAuthorityUtil.MANAGER, true);
        log.info("session create [{}]", session.getAttribute(MyAuthorityUtil.MANAGER));

        return (Long) session.getAttribute("memberID");

    }

}
