package com.example.zerobase.zerobaseminiassignment.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenModel {
    //Access Token을 HTTP 요청의 Authorization 헤더에 포함하여 전송
    // 인증 타입 ex) Authorization: Bearer <access_token> 사용예정

    public enum GrantType {
        ACCESS, REFRESH
    }
    private String accessToken;
    private String refreshToken;

}
