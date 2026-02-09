package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.common.cookie.CookieSpec;
import com.inspire.inspirebe.common.cookie.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final CookieUtils cookieUtils;
    private static final String PREFIX = "RT:";
    private static final String COOKIE_NAME = "refresh_token";

    @Override
    public void storeRefreshToken(Long userId, String token) {
        redisTemplate.opsForValue().set(
                PREFIX + userId,
                token,
                14,
                TimeUnit.DAYS
        );
    }

    @Override
    public void addRefreshTokenCookie(HttpServletResponse servletResponse, String token) {
        cookieUtils.addCookie(servletResponse, CookieSpec.builder()
                .name("refresh_token")
                .value(token)
                .path("/")
                .httpOnly(true)
                .maxAge(14 * 24 * 60 * 60)
                .build());
    }

    @Override
    public String getRefreshToken(Long userId) {
        String token = redisTemplate.opsForValue().get(PREFIX + userId);
        return token;
    }
    @Override
    public boolean validateRefreshToken(Long userId, String token) {
        return token.equals(getRefreshToken(userId));
    }

    @Override
    public void deleteRefreshToken(Long userId) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + userId))) {
            log.info("RefreshTokenService Token Not Found");
            log.info("RefreshTokenService But It Works");
        }
        redisTemplate.delete(PREFIX + userId);
    }

    @Override
    public void clearRefreshTokenCookie(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        cookieUtils.deleteCookie(servletRequest, servletResponse, COOKIE_NAME);
    }
}
