package com.example.zerobase.zerobaseminiassignment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

@Slf4j
@Configuration
public class MyRedisConfig {

    /**
     * lettuce
     * RedisConnextionFatory 인터페이스 통해 Lettuce 반환
     * yaml 저장 host, port 연결
     * @return
     */
    @Value("${spring.data.redis.host}")
    String redisHost;

    @Value("${spring.data.redis.port}")
    int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        log.info("redis connection : "+redisHost+" "+redisPort);
        return new LettuceConnectionFactory(redisHost,redisPort);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}