package com.inspire.inspirebe.attend.controller;

import com.inspire.inspirebe.attend.dto.AttendRequestDTO;
import com.inspire.inspirebe.attend.dto.AttendResponseDTO;
import com.inspire.inspirebe.attend.service.AttendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "출퇴근 관리 API", description = "QR 기반 출퇴근 관리 기능")
@RestController
@RequestMapping("/api/v1/attends")
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService;

    @Operation(
            summary = "월별 출퇴근 조회",
            description = "로그인한 사용자의 특정 연도/월 출퇴근 기록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/me")
    public ResponseEntity<List<AttendResponseDTO>> getAttends(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(description = "조회 연도", example = "2026")
            @RequestParam("year") Integer year,

            @Parameter(description = "조회 월 (1~12)", example = "2")
            @RequestParam("month") Integer month,

            @Parameter(description = "조회 일 (1~31)", example = "14")
            @RequestParam("day") Integer day) {
        return ResponseEntity.ok(attendService.getAllAttends(userId, year, month, day));
    }

    @Operation(
            summary = "QR 출퇴근 체크",
            description = "QR 토큰을 통해 출퇴근을 기록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "출퇴근 처리 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 QR 토큰"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/check")
    public ResponseEntity<Void> attendCheck(

            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "출퇴근 요청 정보",
                    required = true
            )
            @RequestBody AttendRequestDTO request) {
        
        attendService.attend(userId, request.getQrToken());

        return ResponseEntity.noContent().build();
    }
}