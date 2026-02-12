package com.inspire.inspirebe.admin.controller;

import com.inspire.inspirebe.attend.dto.AttendUpdateDTO;
import com.inspire.inspirebe.attend.dto.AttendResponseDTO;
import com.inspire.inspirebe.attend.service.AttendService;
import com.inspire.inspirebe.admin.dto.AdminUserUpdateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "관리자 전용 API (회원 관리 및 출결 수정)") //
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AttendService attendService;

   @Operation(summary = "근무자 정보 수정")
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    content = @io.swagger.v3.oas.annotations.media.Content(
        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AdminUserUpdateDTO.class),
        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
            name = "기본 수정 예시",
            value = "{ \"name\": \"tester1\", \"contact\": \"010-1234-5678\", \"salary\": 12000, \"status\": \"ACTIVE\" }" //
        )
    )
)

    @PatchMapping("/users/{id}")
    public ResponseEntity<String> updateUser(
            @Parameter(description = "수정할 사용자의 고유 ID", example = "1") @PathVariable Long id,
            @RequestBody AdminUserUpdateDTO userUpdateDTO) { //
        userService.updateUserByAdmin(id, userUpdateDTO);
        return ResponseEntity.ok("근무자 정보가 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "전체 회원 조회", description = "상태(status) 및 역할(role)별로 필터링하여 회원 목록을 조회합니다.") //
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @Parameter(description = "사용자 상태 (ACTIVE, SUSPENDED 등)") @RequestParam(required = false) String status,
            @Parameter(description = "사용자 역할 (USER, ADMIN)") @RequestParam(required = false) String role) {
        return ResponseEntity.ok(userService.getAllUsers(status, role));
    }

    @Operation(summary = "근무자 상세 조회", description = "특정 근무자의 상세 프로필 데이터를 제공합니다.") //
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserDetail(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(summary = "근무자 삭제", description = "시스템에서 특정 사용자를 완전히 삭제합니다.") //
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("근무자가 삭제되었습니다.");
    }

    @Operation(summary = "정산 처리", description = "급여 및 근태 데이터를 바탕으로 정산을 수행합니다.") //
    @PostMapping("/settlements")
    public ResponseEntity<?> settlement() {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "출결 기록 수정", description = "잘못 기록된 QR 출결 데이터를 관리자가 직접 수정합니다.") //
    @PatchMapping("/attends/{id}")
    public ResponseEntity<String> updateAttend(
            @Parameter(description = "수정할 출결 데이터의 고유 ID") @PathVariable Long id,
            @RequestBody AttendUpdateDTO attendUpdateDTO) { //
        attendService.updateAttend(id, attendUpdateDTO);
        return ResponseEntity.ok("근무자 정보가 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "전체 출결 조회", description = "사용자 ID, 연도, 월별로 필터링하여 전체 출결 기록을 조회합니다.") //
    @GetMapping("/attends")
    public ResponseEntity<List<AttendResponseDTO>> getAllAttends(
            @Parameter(description = "조회할 사용자의 ID") @RequestParam(value = "userId", required = false) Long userId,
            @Parameter(description = "조회 연도 (예: 2026)") @RequestParam(value = "year", required = false) Integer year,
            @Parameter(description = "조회 월 (1~12)") @RequestParam(value = "month", required = false) Integer month) {
        return ResponseEntity.ok(attendService.getAllAttends(userId, year, month));
    }

    @Operation(summary = "출결 상세 조회", description = "특정 출결 기록의 상세 내용을 확인합니다.") //
    @GetMapping("/attends/{id}")
    public ResponseEntity<AttendResponseDTO> getAttend(@PathVariable Long id) {
        return ResponseEntity.ok(attendService.getAttend(id));
    }

    @Operation(summary = "출결 기록 삭제", description = "불필요하거나 잘못된 출결 기록을 삭제합니다.") //
    @DeleteMapping("/attends/{id}")
    public ResponseEntity<String> deleteAttend(@PathVariable Long id) {
        attendService.deleteAttend(id);
        return ResponseEntity.ok("출결 기록이 삭제되었습니다.");
    }
}