package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    private static final String TOKEN_BLACK_LIST="token-black-list";
    private static final String POST_LIST="post-list";

    private final Long redisTTLTime = 30L;
    public RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    // set 수행 전 get을 통해 key를 셋팅 !
    public void addBlackList(String token){
        String key = TOKEN_BLACK_LIST;
        redisTemplate.opsForValue().set(getKey(key, token), "true");

        // 만료시간 테스트
        redisTemplate.expire(getKey(key, token),redisTTLTime, TimeUnit.SECONDS);

    }

    public boolean isBlackList(String token){
        String key = TOKEN_BLACK_LIST;
        return Objects.equals(redisTemplate.opsForValue().get(getKey(key,token)), "true");
    }

    public void addPostList(PostModel postModel){
        String key = POST_LIST;
        // post ID 추가합니다.
        redisTemplate.opsForValue().set(getKey(key,postModel.getPostId()), postModel);
    };

    // 레디스의 Scan을 통해 데이터를 조회합니다.
    public List<PostModel> getPostList(long count, String cursor){
        List<PostModel> postModels = new ArrayList<>();
        String pattern = POST_LIST +":"+"*";

        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(count).build();
        Cursor<byte[]> redisCursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        while (redisCursor.hasNext()) {
            log.info("redisCursor :"+redisCursor.toString());
        }

        return postModels;
    }

    // 변도의 키관리가 필요해 보입니다.
    private String getKey(String key, Object token) {
        return key + ":" + token;
    }
}
