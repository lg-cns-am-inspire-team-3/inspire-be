package com.inspire.inspirebe.user.controller;

import java.util.List;

import com.inspire.inspirebe.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inspire.inspirebe.user.dto.UserApprovalRequest;
import com.inspire.inspirebe.user.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 1. 회원가입 승인
     * 로직: SUSPENDED -> ACTIVE 상태 변경 및 시급 저장
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Long id) {
        adminService.approveUser(id);
        return ResponseEntity.ok("회원 승인이 완료되었습니다.");
    }
    // 이미 승인 된것도 중복 승인이 되서 수정이 필요 


    /**
     * 2. 전체 회원 조회
     * 모든 상태의 사용자 리스트를 반환합니다.
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    /**
     * 3. 승인 대기 중인 회원 조회
     * GET /api/v1/admin/users/suspended
     * 설명: 관리자 메인에서 승인 버튼을 눌러야 할 대기자들만 보여줍니다.
     */
    @GetMapping("/suspended")
    public ResponseEntity<List<UserResponseDTO>> getSuspendedUsers() {
        return ResponseEntity.ok(adminService.getSuspendedUsers());
    }

    /**
     * 4. 근무자 상세 조회
     * GET /api/v1/admin/users/{id}
     * 설명: 목록에서 특정 근무자를 클릭했을 때 상세 페이지 데이터를 제공합니다.
     */
    // @GetMapping("/{id}")
    // public ResponseEntity<UserResponseDTO> getUserDetail(@PathVariable Long id) {
    // return ResponseEntity.ok(adminService.getUserDetail(id));
    // }

    /**
     * 5. 근무자 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("근무자가 삭제되었습니다.");
    }
}