package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    // 로그인 및 토큰 발급
    void login(HttpServletResponse servletResponse, UserLoginDTO loginDTO);

    // 토큰 재발급
    TokenResponseDTO reissue(HttpServletResponse servletResponse, String refreshToken);

    // 로그아웃 및 토큰 무효화
    void logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse, String refreshToken);
}