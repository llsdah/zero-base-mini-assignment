package com.example.zerobase.zerobaseminiassignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.embedded.RedisServer;

@SpringBootTest
public class RedisIntegrationTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        // Embedded Redis를 시작합니다.
        RedisServer.builder().port(6379).setting("maxheap 128M").build().start();
    }

    @AfterEach
    void tearDown() {
        // Embedded Redis를 종료합니다.
        RedisServer.builder().port(6379).setting("maxheap 128M").build().stop();
    }

    @Test
    void testRedisOperations() {
        System.out.println("redis test 1");
        // Redis에 데이터를 저장하고 조회하는 간단한 테스트 코드
        redisTemplate.opsForValue().set("sampleKey", "sampleValue");
        String value = redisTemplate.opsForValue().get("sampleKey");

        System.out.println("redis test 2");
        assert "sampleValue".equals(value);
    }
}
