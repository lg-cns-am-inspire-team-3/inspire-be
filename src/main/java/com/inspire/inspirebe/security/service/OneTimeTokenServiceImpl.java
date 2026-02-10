package com.inspire.inspirebe.security.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class OneTimeTokenServiceImpl implements OneTimeTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "ONETIME:";

    
    public OneTimeTokenServiceImpl(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void storeOneTimeToken(String token, String OAuth2UserVo) {
        // 토큰을 Redis에 5분 동안 저장합니다.
        redisTemplate.opsForValue().set(PREFIX + token, OAuth2UserVo, 5, TimeUnit.MINUTES);
    }

    @Override
    public String getOAuth2User(String token) {
        return redisTemplate.opsForValue().get(PREFIX + token);
    }

    @Override
    public void deleteOneTimeToken(String token) {
        redisTemplate.delete(PREFIX + token);
    }

    @Override
    public boolean hasTokenWithKey(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + token));
    }
}