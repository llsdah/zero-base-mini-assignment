package com.example.zerobase.zerobaseminiassignment.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class MySessionUtil {

    public static Long getSessionMemberId(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (Long) session.getAttribute("memberID");
    }

}
