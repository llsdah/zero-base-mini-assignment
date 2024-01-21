package com.example.zerobase.zerobaseminiassignment.common;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class MyJwtUtil {

    private static MemberModel checkLogin(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberModel memberModel;

        if(authentication.getPrincipal() instanceof MemberModel){
            memberModel = (MemberModel) authentication.getPrincipal();
        }else {
            throw new RuntimeException("[ERROR] need to login");
        }
        return memberModel;
    }

    public static MemberModel getMember(){
        log.info("MyJwtUtil getMember");
        return checkLogin();
    }

    public static Long getMemberId(){
        log.info("MyJwtUtil getMemberId");
        return checkLogin().getMemberId();
    }
}
