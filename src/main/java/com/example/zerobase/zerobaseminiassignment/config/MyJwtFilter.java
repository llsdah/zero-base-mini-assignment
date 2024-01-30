package com.example.zerobase.zerobaseminiassignment.config;


import com.example.zerobase.zerobaseminiassignment.common.MyJwtProvider;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import com.example.zerobase.zerobaseminiassignment.service.RedisService;
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
    @Autowired
    private RedisService redisService;

    public MyJwtFilter(MyJwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String memberID = null;
        String jwt = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            log.info("tokenBlacklistService "+ redisService.isBlackList(jwt));
            /**
             * 블랙리스트 토큰인지 체크 추가 즉, 10초 이내 재 로그인 방지.. 뭐 그냥,!
             */
            if(redisService.isBlackList(jwt)){
                log.info("It is tokenBlacklistService");
                SecurityContextHolder.clearContext();
                jwt="";// 초기화
                throw new RuntimeException("wait maybe ten seconds");
            };

            memberID = jwtProvider.extractMemberId(jwt);
        }

        if (memberID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            MemberModel memberModel = memberManageService.find(Long.parseLong(memberID));
            // 토큰이 유효하면, 사용자 인증을 수행하고 SecurityContext에 추가

            if (memberModel != null && jwtProvider.validateToken(jwt, memberModel)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(memberModel, null, memberModel.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);
    }
}
