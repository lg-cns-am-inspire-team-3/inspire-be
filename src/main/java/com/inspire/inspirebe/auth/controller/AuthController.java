package com.inspire.inspirebe.auth.controller;

import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import com.inspire.inspirebe.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${front.redirect.url}")
    private String redirectURL;
    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            @CookieValue(name = "refresh_token", required = false) String refreshToken) {
    
        authService.logout(servletRequest, servletResponse, refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse servletResponse, @RequestBody UserLoginDTO userLoginDTO) {

        authService.login(servletResponse, userLoginDTO);
        URI uri = URI.create(redirectURL);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(HttpServletResponse servletResponse, @CookieValue(name = "refresh_token") String refreshToken) {
        TokenResponseDTO accessToken = authService.reissue(servletResponse, refreshToken);

        return ResponseEntity.ok(accessToken);
    }
}