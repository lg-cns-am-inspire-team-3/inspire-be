package com.inspire.inspirebe.auth.controller;

import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import com.inspire.inspirebe.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${frontend.url}")
    private String frontendURL;
    private final AuthService authService;
    /**
     * 사용자 로그인
     * 성공 시 쿠키에 Refresh Token을 설정하고 200 OK를 반환합니다.
     * 실패 시 GlobalExceptionHandler에 의해 에러 규격이 반환됩니다.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse servletResponse, @RequestBody UserLoginDTO userLoginDTO) {
        log.info("Login request received for ID: {}", userLoginDTO.getLoginId());

        authService.login(servletResponse, userLoginDTO);

        // 프론트엔드(Axios) 그냥 성공임
        return ResponseEntity.noContent().build();
    }

    /**
     * 로그아웃
     * Refresh Token 삭제 및 쿠키 초기화를 수행합니다.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            @CookieValue(name = "refresh_token", required = false) String refreshToken) {

        authService.logout(servletRequest, servletResponse, refreshToken);
        return ResponseEntity.noContent().build();
    }

    /**
     * 토큰 재발급
     * 유효한 Refresh Token 확인 후 새로운 Access/Refresh Token 세트를 발급합니다.
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(
            HttpServletResponse servletResponse,
            @CookieValue(name = "refresh_token") String refreshToken) {

        TokenResponseDTO accessToken = authService.reissue(servletResponse, refreshToken);
        return ResponseEntity.ok(accessToken);
    }
}