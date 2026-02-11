package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import com.inspire.inspirebe.common.jwt.JwtProvider;
import com.inspire.inspirebe.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // 1. 빨간 줄의 범인! 이름을 JwtTokenService로 바꿨어요.
    private final JwtTokenService jwtTokenService; 
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void login(HttpServletResponse servletResponse, UserLoginDTO loginDTO) {
        // 1. 자격 증명 확인
        Long userId = userService.validateCredentials(loginDTO.getLoginId(), loginDTO.getPassword());
        long now = System.currentTimeMillis();

        // 2. Refresh Token 생성 및 저장
        String refreshToken = jwtProvider.createRefreshToken(userId, now);
        jwtTokenService.storeRefreshToken(userId, refreshToken); // save -> store
        jwtTokenService.addRefreshTokenCookie(servletResponse, refreshToken);
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse, String refreshToken) {
        Long userId = jwtProvider.getUserIdFromRefresh(refreshToken);
        jwtTokenService.deleteRefreshToken(userId);
        jwtTokenService.clearRefreshTokenCookie(servletRequest, servletResponse);
    }

    @Override
    @Transactional
    public TokenResponseDTO reissue(HttpServletResponse servletResponse, String fromToken) {
        // 1. JWT 검증
        if (!jwtProvider.validateRefreshToken(fromToken)) {
            throw new RuntimeException("유효하지 않은 token입니다.");
        }

        // 2. 저장소 일치 여부 확인
        Long userId = jwtProvider.getUserIdFromRefresh(fromToken);
        if(!jwtTokenService.validateRefreshToken(userId, fromToken)) { // matches -> validateRefreshToken
            throw new RuntimeException("유효하지 않은 token입니다.");
        }

        // 3. 새로운 토큰 생성
        String role = userService.getUserRole(userId);
        long now = System.currentTimeMillis();
        String accessToken = jwtProvider.createAccessToken(userId, role, now);
        String refreshToken = jwtProvider.createRefreshToken(userId, now);

        // 4. 갱신된 Refresh Token 저장 및 쿠키 설정
        jwtTokenService.storeRefreshToken(userId, refreshToken);
        jwtTokenService.addRefreshTokenCookie(servletResponse, refreshToken);

        return TokenResponseDTO.builder()
                .token(accessToken)
                .expires(jwtProvider.getTokenExpiresInSeconds())
                .build();
    }
}