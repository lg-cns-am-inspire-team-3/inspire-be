package com.inspire.inspirebe.attend.controller;

import com.inspire.inspirebe.attend.dto.AttendRequestDTO;
import com.inspire.inspirebe.attend.dto.AttendResponseDTO;
import com.inspire.inspirebe.attend.service.AttendService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attends")
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService;

    @GetMapping("/me")
    public ResponseEntity<List<AttendResponseDTO>> getAttends(@AuthenticationPrincipal Long userId,
                                                              @RequestParam("year") Integer year,
                                                              @RequestParam("month") Integer month,
                                                              @RequestParam("day") Integer day) {
        return ResponseEntity.ok(attendService.getAllAttends(userId, year, month, day));
    }
    
    @PostMapping("/check")
    public ResponseEntity<Void> attendCheck(
            @AuthenticationPrincipal Long userId, // 현재 요청을 보낸 로그인된 사용자 정보를 SecurityContext에서 꺼내서 이 파라미터에 넣기
            @RequestBody AttendRequestDTO request) {
        
        attendService.attend(userId, request.getQrToken());

        return ResponseEntity.noContent().build();
    }
}