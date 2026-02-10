package com.inspire.inspirebe.auth.service;

import com.inspire.inspirebe.common.jwt.JwtProvider;
import com.inspire.inspirebe.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private JwtTokenService jwtTokenService;

    @Test
    @DisplayName("로그아웃 성공: 리프레시 토큰 삭제 및 쿠키 만료 로직 검증")
    void logout_success() {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String refreshToken = "valid-refresh-token";
        Long userId = 1L;

        // Stubbing: 토큰에서 유저 ID 추출 (혹시 모르니 any() 사용)
        when(jwtProvider.getUserIdFromRefresh(any())).thenReturn(userId);

        // When
        authService.logout(request, response, refreshToken);

        // Then
        verify(jwtTokenService, times(1)).deleteRefreshToken(userId);
        verify(jwtTokenService, times(1)).clearRefreshTokenCookie(request, response);
    }

    @Test
    @DisplayName("토큰 재발급 성공: 리프레시 토큰이 유효하면 액세스 토큰을 새로 발급한다")
    void reissue_success() {
        // 1. Given (준비)
        HttpServletResponse response = mock(HttpServletResponse.class);
        String refreshToken = "valid-refresh-token";
        Long userId = 1L;

        // 👇 [핵심 수정] "뭐가 들어오든 검사 무조건 통과(true) 시켜!" (any() 사용)
        when(jwtProvider.validateRefreshToken(any())).thenReturn(true);

        // 유저 ID 추출도 관대하게 설정
        when(jwtProvider.getUserIdFromRefresh(any())).thenReturn(userId);

        // 👇 [핵심 수정] "저장소 검사도 무조건 통과(true) 시켜!"
        when(jwtTokenService.validateRefreshToken(any(), any())).thenReturn(true);

        // 유저 권한 조회 설정
        when(userService.getUserRole(userId)).thenReturn("ROLE_USER");

        // 액세스 토큰 생성 설정 (관대하게 any 사용)
        when(jwtProvider.createAccessToken(any(), any(), Mockito.anyLong())).thenReturn("new-access-token");
        
        // 리프레시 토큰 생성 설정
        when(jwtProvider.createRefreshToken(any(), Mockito.anyLong())).thenReturn("new-refresh-token");

        // 만료 시간 조회 설정
        when(jwtProvider.getTokenExpiresInSeconds()).thenReturn(3600L);

        // 2. When (실행)
        authService.reissue(response, refreshToken);

        // 3. Then (검증)
        verify(jwtProvider, times(1)).createAccessToken(eq(userId), anyString(), Mockito.anyLong());
        verify(jwtTokenService, times(1)).storeRefreshToken(eq(userId), anyString());
    }
}