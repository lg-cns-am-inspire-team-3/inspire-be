package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.auth.repository.AuthRepository;
import com.inspire.inspirebe.auth.util.JwtProvider;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate; 

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