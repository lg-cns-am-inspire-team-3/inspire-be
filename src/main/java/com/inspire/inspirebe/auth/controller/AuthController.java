package com.inspire.inspirebe.auth.controller;

import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import com.inspire.inspirebe.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API (로그인, 로그아웃, 토큰 재발급)")
public class AuthController {

    @Value("${frontend.url}")
    private String frontendURL;
    private final AuthService authService;

    @Operation(summary = "사용자 로그인", description = "아이디와 비밀번호로 로그인합니다. 성공 시 쿠키에 Refresh Token이 설정됩니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "로그인 성공"),
        @ApiResponse(responseCode = "401", description = "로그인 실패 (아이디/비밀번호 불일치)"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse servletResponse, @RequestBody UserLoginDTO userLoginDTO) {
        log.info("Login request received for ID: {}", userLoginDTO.getLoginId());

        authService.login(servletResponse, userLoginDTO);

        // 성공 시 204 No Content 반환
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "로그아웃", description = "서버 측 세션(Refresh Token)을 무효화하고 쿠키를 초기화합니다.")
    @ApiResponse(responseCode = "204", description = "로그아웃 성공")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            @CookieValue(name = "refresh_token", required = false) String refreshToken) {

        authService.logout(servletRequest, servletResponse, refreshToken);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "토큰 재발급", description = "쿠키의 Refresh Token을 확인하여 새로운 Access Token을 발급합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "재발급 성공"),
        @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 Refresh Token")
    })
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(
            HttpServletResponse servletResponse,
            @CookieValue(name = "refresh_token") String refreshToken) {

        TokenResponseDTO accessToken = authService.reissue(servletResponse, refreshToken);
        return ResponseEntity.ok(accessToken);
    }
}