package com.inspire.inspirebe;

import com.inspire.inspirebe.user.dto.UserApprovalRequest;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private UserCredentialsRepository credentialsRepository;

    @Test
    void approveUserTest() {

    }

    @Test
    void getAllUsersTest() {

    }

    /*
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
     */
}
