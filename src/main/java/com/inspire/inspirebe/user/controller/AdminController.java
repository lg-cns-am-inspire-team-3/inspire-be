package com.inspire.inspirebe.user.controller;

import java.util.List;

import com.inspire.inspirebe.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inspire.inspirebe.user.dto.UserApprovalRequest;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 1. 회원가입 승인 및 시급(salary) 설정
    @PatchMapping("/{id}")
    public ResponseEntity<String> approveUser(
            @PathVariable Long id, 
            @RequestBody UserApprovalRequest request) {
        
        adminService.approveUser(id, request.getSalary());
        return ResponseEntity.ok("회원 승인이 완료되었습니다.");
    }

    // 2. 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
}
