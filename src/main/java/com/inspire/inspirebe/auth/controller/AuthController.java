package com.inspire.inspirebe.auth.controller;

// import com.inspire.inspirebe.auth.dto.LogoutRequestDto;
import com.inspire.inspirebe.auth.dto.TokenResponseDTO;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import com.inspire.inspirebe.auth.service.AuthService;
import com.inspire.inspirebe.common.cookie.CookieSpec;
import com.inspire.inspirebe.common.cookie.CookieUtils;
import com.inspire.inspirebe.common.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
// import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtils cookieUtils;
    private final JwtProvider jwtProvider;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        /*
         * access token은 필터에서 처리
         * 만약 invalid token이면 401 UNAUTHORIZED가 반환된 상태
         * (편의상) refresh token의 valid와 상관없이 redis에서는 userId에 맞는 값만 지워주면 됨
         */

        authService.logout(servletRequest, servletResponse, refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse servletResponse, @RequestBody UserLoginDTO userLoginDTO) {

        authService.login(servletResponse, userLoginDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(HttpServletResponse servletResponse, @CookieValue(name = "refresh_token") String refreshToken) {
        TokenResponseDTO accessToken = authService.reissue(servletResponse, refreshToken);

        return ResponseEntity.ok(accessToken);
    }
}