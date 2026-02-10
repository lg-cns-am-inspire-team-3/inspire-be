package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    TokenResponseDTO reissue(HttpServletResponse servletResponse, String refreshToken);
    void logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse, String refreshToken);
    void login(HttpServletResponse servletResponse, UserLoginDTO userLoginDTO);
}