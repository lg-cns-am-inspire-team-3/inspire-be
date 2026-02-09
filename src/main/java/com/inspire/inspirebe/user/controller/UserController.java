package com.inspire.inspirebe.user.controller;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserSignupRequest;
import com.inspire.inspirebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * [POST] /api/v1/users/signup
     * 일반 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    /**
     * [GET] /api/v1/users/check-id/{loginId}
     * 아이디 중복 여부를 확인합니다.
     */
    @GetMapping("/check-id/{loginId}")
    public ResponseEntity<Boolean> checkId(@PathVariable String loginId) {
        boolean isDuplicated = userService.isIdDuplicated(loginId);
        return ResponseEntity.ok(isDuplicated);
    }

    // --- 조장님이 추가하신 CRUD API ---

    // Create (관리자용 혹은 직접 생성용)
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO request) {
        UserResponseDTO response = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Read (단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id) {
        UserResponseDTO response = userService.getUser(id);
        return ResponseEntity.ok(response);
    }

    // Update (수정 - 임시)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser() {
        return null; 
    }

    // Delete (삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}