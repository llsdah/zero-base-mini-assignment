package com.example.zerobase.zerobaseminiassignment.common;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class MyJwtProvider {

    private String secret;
    private String expiration;
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public MyJwtProvider(@Value("${jwt.secret}")String secret,@Value("${jwt.expiration}") String expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }


    // 토큰 생성
    public String generateToken(MemberModel userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, String.valueOf(userDetails.getMemberId()));
    }

    // 토큰에서 유저 id 추출
    public String extractMemberId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        Date date = extractExpiration(token);
        boolean flag = date.before(new Date());
        return flag;
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token, MemberModel memberModel) {
        final String memberId = extractMemberId(token);
        return (memberId.equals(memberModel.getMemberId()+"") && !isTokenExpired(token));
    }

    // 토큰에서 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 토큰에서 모든 클레임 추출, validation 포함
    private Claims extractAllClaims(String token) {

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    // 토큰 만료 날짜 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰 생성
    private String createToken(Map<String, Object> claims, String subject) {

        Date date = new Date();
        long time = System.currentTimeMillis() +  Long.parseLong(expiration);
        date.setTime(time);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(date)
                .signWith(key)
                .compact();
    }

}
