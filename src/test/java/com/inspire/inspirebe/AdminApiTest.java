package com.inspire.inspirebe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire.inspirebe.user.dto.UserApprovalRequest;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private UserCredentialsRepository credentialsRepository;
    private ObjectMapper objectMapper;

    @Test
    void approveUserTest() throws Exception {
        // Given
        Long userId = 1L; // 테스트용 ID
        UserApprovalRequest request = new UserApprovalRequest(10000);

        // When & Then
        mockMvc.perform(patch("/admin/" + userId) 
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("회원 승인이 완료되었습니다."))
                .andDo(print());
    }

    @Test
    void getAllUsersTest() throws Exception {
        // When & Then
        mockMvc.perform(get("/admin") // 컨트롤러의 정확한 경로를 확인하세요
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // 응답이 리스트 형태인지 확인 (JSON Path 사용)
                .andExpect(jsonPath("$").isArray()) 
                .andDo(print());
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
