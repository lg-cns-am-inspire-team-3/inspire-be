package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import com.inspire.inspirebe.common.jwt.JwtProvider;
import com.inspire.inspirebe.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void login(HttpServletResponse servletResponse, UserLoginDTO loginDTO) {
        Long userId = userService.validateCredentials(loginDTO.getLoginId(), loginDTO.getPassword());

        String refreshToken = jwtProvider.createRefreshToken(userId, System.currentTimeMillis());
        jwtTokenService.storeRefreshToken(userId, refreshToken);
        jwtTokenService.addRefreshTokenCookie(servletResponse, refreshToken);
    }

    @Override
    public void logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Long userId) {
        jwtTokenService.deleteRefreshToken(userId);
        jwtTokenService.clearRefreshTokenCookie(servletRequest, servletResponse);
    }

    @Override
    public String reissue(HttpServletResponse servletResponse, String fromToken) {
        // jwt 자체 검증
        if (!jwtProvider.validateRefreshToken(fromToken)) {
            throw new RuntimeException("유효하지 않은 token입니다.");
        }

        // refresh token은 있는데, redis랑 다른 경우
        // e.g.) DB에서 의도적으로 삭제
        Long userId = jwtProvider.getUserId(fromToken);
        if(!jwtTokenService.validateRefreshToken(userId, fromToken)) {
            throw new RuntimeException("유효하지 않은 token입니다.");
        }

        /*
         * 실제로 다른 도메인의 정보를 얻을 때에는 API를 통해 정보를 얻거나
         * 이벤트 기반의 Read Model을 사용하는게 아래처럼 하는 것보다 좋습니다.
         */
        String role = userService.getUserRole(userId);
        long now = System.currentTimeMillis();
        String accessToken = jwtProvider.createAccessToken(userId, role, now);
        String refreshToken = jwtProvider.createRefreshToken(userId, now);

        jwtTokenService.storeRefreshToken(userId, refreshToken);
        jwtTokenService.addRefreshTokenCookie(servletResponse, refreshToken);

        return accessToken;
    }

}