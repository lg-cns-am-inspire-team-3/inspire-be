package com.inspire.inspirebe.user.controller;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * [POST] /api/v1/users
     * 일반 회원가입
     */
    @PostMapping("")
    public ResponseEntity<String> signup(@RequestBody UserCreateDTO request) {
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

    // Read (단건 조회)
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal Long id) {
        UserResponseDTO response = userService.getUser(id);
        return ResponseEntity.ok(response);
    }

    // Update (수정 - 임시)
    @PatchMapping("/me")
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    // Delete (삭제)
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}