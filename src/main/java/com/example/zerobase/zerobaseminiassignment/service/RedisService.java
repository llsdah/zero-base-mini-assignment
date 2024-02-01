package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    private static final String TOKEN_BLACK_LIST="token-black-list";
    private static final String POST_LIST="post-list";
    private static final String POST_COUNT="post-count";

    private final Long redisTTLTime = 30L;
    public RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisService(RedisTemplate<String,Object> redisTemplate,ObjectMapper objectMapper){
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
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

    public void addPostListCount(Integer num){
        try {
            String jsonValue = objectMapper.writeValueAsString(num);
            redisTemplate.opsForValue().set(POST_COUNT, jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getValueForPostCount() {
        // Redis에서 값을 조회
        Object value = redisTemplate.opsForValue().get(POST_COUNT);

        // 값이 null 또는 $-1인 경우 처리
        if (value == null) {
            // 값이 없는 경우의 처리를 여기에 작성
            System.out.println("Key does not exist in Redis.");
            return 0; //
        }
        int num = Integer.parseInt(value.toString());
        // 값이 있는 경우
        return num;
    }

    public void addPostList(PostModel postModel){
        log.info("redis service addPostList");
        String key = POST_LIST;
        String jsonValue = null;

        try {
            jsonValue = objectMapper.writeValueAsString(postModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("redis key : "+key);
        log.info("redis jsonValue : "+jsonValue);

        // post ID 추가합니다.
        redisTemplate.opsForValue().set(getKey(key,postModel.getPostId()), jsonValue);
        //redisTemplate.opsForList().leftPush(getKey(key,postModel.getPostId()), jsonValue);
    };

    // 레디스의 Scan을 통해 데이터를 조회합니다.
    public List<PostModel> getPostList(int visible, int pageCount){
        log.info("redis service getPostList");

        List<PostModel> postModels = new ArrayList<>();
        String pattern = POST_LIST +":"+"*";

        log.info("redis key : "+pattern);

        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(visible).build();
        Cursor<byte[]> redisCursor = redisTemplate.getConnectionFactory().getConnection().scan(options);
        List<String> keyList = new ArrayList<>();
        //objectMapper.readValue(jsonValue, type)
        int startPoint = visible*(pageCount-1);

        int nowIndex = 0;
        int postCount = 0;

        while (redisCursor.hasNext()) {
            log.info("redisCursor :"+redisCursor.toString());
            log.info("nowIndex :"+nowIndex);
            log.info("postCount :"+postCount);

            // 시작부분까지 인덱스 증가
            if(startPoint>=nowIndex){
                nowIndex++;
                continue;
            }

            if(postCount<pageCount){
                byte[] keyBytes = redisCursor.next();
                String key = new String(keyBytes, StandardCharsets.UTF_8);
                keyList.add(key);
                postCount++;
            }else{
                break;
            }

        }

        //Collections.sort(keyList);

        return postModels;
    }


    // 변도의 키관리가 필요해 보입니다.
    private String getKey(String key, Object token) {
        return key + ":" + token;
    }

    public void deleteKeysStartingWithPost() {
        String pattern = POST_LIST +":"+"*";
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();

        try (Cursor<byte[]> redisCursor = redisTemplate.getConnectionFactory().getConnection().scan(options)) {
            while (redisCursor.hasNext()) {
                byte[] keyBytes = redisCursor.next();
                // 스캔된 키를 삭제
                redisTemplate.delete(new String(keyBytes, StandardCharsets.UTF_8));
            }
        }
    }

}
