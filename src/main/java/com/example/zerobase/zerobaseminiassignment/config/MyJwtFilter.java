package com.example.zerobase.zerobaseminiassignment.config;


import com.example.zerobase.zerobaseminiassignment.common.MyJwtProvider;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class MyJwtFilter extends OncePerRequestFilter {

    @Autowired
    private final MyJwtProvider jwtProvider;

    @Autowired
    private MemberManageService memberManageService;

    public MyJwtFilter(MyJwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("MyJwtFilter doFilterInternal");
        final String authorizationHeader = request.getHeader("Authorization");
        log.info("MyJwtFilter authorizationHeader : "+authorizationHeader);
        String memberID = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        log.info("MyJwtFilter jwt : "+jwt);
            memberID = jwtProvider.extractMemberId(jwt);
        log.info("MyJwtFilter memberID : "+memberID);
        }

        log.info("MyJwtFilter SecurityContextHolder.getContext().getAuthentication() : "+SecurityContextHolder.getContext().getAuthentication());
        if (memberID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            MemberModel memberModel = memberManageService.find(Long.parseLong(memberID));
            log.info("memberModel get"+memberModel.toString());
            // 토큰이 유효하면, 사용자 인증을 수행하고 SecurityContext에 추가
            if (jwtProvider.validateToken(jwt, memberModel)) {
                log.info("MyJwtFilter SecurityContext");
                Authentication authentication = new UsernamePasswordAuthenticationToken(memberModel, null, memberModel.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 토큰이 유효하면, 사용자 인증을 수행하고 SecurityContext에 추가

        }
        log.info("MyJwtFilter doFilter");

        filterChain.doFilter(request, response);
    }
}
