package com.inspire.inspirebe.auth.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.inspire.inspirebe.auth.repository.AuthRepository;
import com.inspire.inspirebe.auth.util.JwtProvider;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;

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

    @Override
    @Transactional
    public String login(String loginId) {
        
        UserEntity user = authRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        
        String accessToken = jwtProvider.createAccessToken(user.getLoginId(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getLoginId());

        
        redisTemplate.opsForValue().set(
                "RT:" + user.getLoginId(),
                refreshToken,
                7, 
                TimeUnit.DAYS
        );

        return accessToken;
    }
}