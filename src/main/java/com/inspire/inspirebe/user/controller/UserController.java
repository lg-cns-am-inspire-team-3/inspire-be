package com.inspire.inspirebe.user.controller;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "사용자 관리 API", description = "회원 가입 및 사용자 정보 관리 기능")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원 가입",
            description = "일반 회원 가입을 수행합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류"),
            @ApiResponse(responseCode = "404", description = "이미 존재하는 아이디")
    })
    @PostMapping("")
    public ResponseEntity<String> signup(
            @RequestBody UserCreateDTO request) {

        userService.signup(request);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @Operation(
            summary = "아이디 중복 확인",
            description = "입력한 로그인 ID의 중복 여부를 반환합니다. true = 중복, false = 사용 가능"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "확인 성공")
    })
    @GetMapping("/check-id/{loginId}")
    public ResponseEntity<Boolean> checkId(
            @Parameter(description = "확인할 로그인 ID", example = "testuser")
            @PathVariable String loginId) {

        boolean isDuplicated = userService.isIdDuplicated(loginId);
        return ResponseEntity.ok(isDuplicated);
    }

    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 사용자의 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long id) {

        UserResponseDTO response = userService.getUser(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "내 정보 수정",
            description = "현재 로그인한 사용자의 정보를 수정합니다. null이 아닌 값만 수정됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PatchMapping("/me")
    public ResponseEntity<Void> updateUser(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long id,
            @RequestBody UserUpdateDTO userUpdateDTO) {

        userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "회원 탈퇴",
            description = "현재 로그인한 사용자를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}