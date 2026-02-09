package com.inspire.inspirebe.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtTokenService {
    // refresh token 일치 확인
    public boolean validateRefreshToken(Long userId, String token);
    // refresh token 저장
    public void storeRefreshToken(Long userId, String token);
    // refresh token 불러오기
    public String getRefreshToken(Long userId);
    // refresh token 삭제
    public void deleteRefreshToken(Long userId);
    // refresh token cookie 추가
    public void addRefreshTokenCookie(HttpServletResponse servletResponse, String token);
    // refresh token cookie 삭제
    public void clearRefreshTokenCookie(HttpServletRequest servletRequest, HttpServletResponse servletResponse);

}
