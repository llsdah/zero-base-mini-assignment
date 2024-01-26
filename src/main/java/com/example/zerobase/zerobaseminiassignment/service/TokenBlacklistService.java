package com.example.zerobase.zerobaseminiassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TokenBlacklistService {
    private static final String TOKEN_BLACK_LIST="token-black-list";

    private final RedisTemplate<String, Boolean> redisTemplate;

    @Autowired

    public TokenBlacklistService(RedisTemplate<String,Boolean> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void addBlackList(String token){
        redisTemplate.opsForValue().set(getKey(token),true);
    }

    public boolean isBlackList(String token){
        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(getKey(token)));
    }


    // 모든것
    public Set<String> getAllBlackListToken(){
        return redisTemplate.keys(TOKEN_BLACK_LIST+":");
    }

    private String getKey(String token) {
        return TOKEN_BLACK_LIST + ":" + token;
    }
}
