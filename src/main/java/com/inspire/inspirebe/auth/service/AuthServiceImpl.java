package com.inspire.inspirebe.auth.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final RedisTemplate<String, String> redisTemplate;

   
    public AuthServiceImpl(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(String email) {
        String key = "RT:" + email;
        try {
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                redisTemplate.delete(key);
                log.info("로그아웃 성공: {}", email);
            }
        } catch (Exception e) {
            log.error("Redis 통신 에러: {}", e.getMessage());
        }
    }
}