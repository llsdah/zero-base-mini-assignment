package com.example.zerobase.zerobaseminiassignment.common;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

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

    /**
     * 권한 체크
     * ✅ 권한이 입력되었을때, 해당 권할을 사용자가 가지고 있는지 체크하는 로직
     */
    public static boolean checkAuth(String authName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection< ? extends GrantedAuthority> test = authentication.getAuthorities(); //최초에 생성할떄 1개의 권한만 담아 준다.
        for( GrantedAuthority grantedAuthority : test){
            if(grantedAuthority.getAuthority().contains(authName)){
                return true;
            }
        }
        return false;
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
