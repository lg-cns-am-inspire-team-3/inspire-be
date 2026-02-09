package com.inspire.inspirebe.auth.controller;

import com.inspire.inspirebe.auth.dto.LogoutRequestDto;
import com.inspire.inspirebe.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto requestDto, HttpServletResponse response) {
        
        // 1. Redis에서 토큰 삭제
        authService.logout(requestDto.getEmail());

        // 2. 쿠키 만료 (수명 0초)
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }
}