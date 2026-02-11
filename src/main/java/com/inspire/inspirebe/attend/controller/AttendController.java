package com.inspire.inspirebe.attend.controller;

import com.inspire.inspirebe.attend.dto.AttendRequestDTO;
import com.inspire.inspirebe.attend.service.AttendService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attend")
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService; 

    
    @PostMapping("/check")
    public ResponseEntity<Void> attendCheck(
            @AuthenticationPrincipal Object principal, // 현재 요청을 보낸 로그인된 사용자 정보를 SecurityContext에서 꺼내서 이 파라미터에 넣기
            @RequestBody AttendRequestDTO request
    ) {
        /*
         * AttendService에서 처리할 내용
         * 1. QR 토큰 유효성 검증
         * 2. 로그인 사용자 출석 가능 여부 확인
         * 3. 중복 출석 방지
         * 4. 출석 기록 저장
         */
        // attendService.attend(principal, request.getQrToken());

        return ResponseEntity.noContent().build();
    }
}