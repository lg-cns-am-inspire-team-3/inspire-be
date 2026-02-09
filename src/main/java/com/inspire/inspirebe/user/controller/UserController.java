package com.inspire.inspirebe.user.controller;

import com.inspire.inspirebe.user.dto.UserSignupRequest;
import com.inspire.inspirebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * [POST] /api/v1/user/signup
     * 회원가입 요청을 처리합니다.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    /**
     * [GET] /api/v1/user/check-id/{loginId}
     * 아이디 중복 여부를 확인합니다.
     */
    @GetMapping("/check-id/{loginId}")
    public ResponseEntity<Boolean> checkId(@PathVariable String loginId) {
        // 중복이면 true, 사용 가능하면 false 반환
        boolean isDuplicated = userService.isIdDuplicated(loginId);
        return ResponseEntity.ok(isDuplicated);
    }
}