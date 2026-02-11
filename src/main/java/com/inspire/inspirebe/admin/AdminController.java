package com.inspire.inspirebe.admin;

import com.inspire.inspirebe.attend.service.AttendService;
import com.inspire.inspirebe.user.dto.AdminUserUpdateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AttendService attendService;

    /**
     * 1. 근무자 정보 수정 (이름, 연락처, 시급, 승인 등 모든 것)
     * PATCH /api/v1/admin/users/{id}
     */
    @PatchMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, AdminUserUpdateDTO userUpdateDTO) {
        userService.updateUserByAdmin(id, userUpdateDTO);
        return ResponseEntity.ok("근무자 정보가 성공적으로 수정되었습니다.");
    }

    /**
     * 2. 전체 회원 조회
     * GET /api/v1/admin/users
     * 사용자 리스트를 반환합니다.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(userService.getAllUsers(status));
    }

    /**
     * 3. 근무자 상세 조회
     * GET /api/v1/admin/users/{id}
     * 설명: 목록에서 특정 근무자를 클릭했을 때 상세 페이지 데이터를 제공합니다.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserDetail(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * 4. 근무자 삭제
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("근무자가 삭제되었습니다.");
    }
    /**
     * 5. 정산
     */
    @PostMapping("/settlements")
    public ResponseEntity<?> settlement() {
        return ResponseEntity.noContent().build();
    }
}

