package com.inspire.inspirebe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire.inspirebe.auth.dto.UserLoginDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 성공: 올바른 아이디와 비밀번호를 입력하면 204 No Content를 반환")
    void loginSuccess() throws Exception {
        // 실제 DB에 있는(혹은 테스트용으로 생성한) 계정 정보
        UserLoginDTO loginDto = new UserLoginDTO();
        loginDto.setLoginId("testuser"); // 실제 존재하는 ID
        loginDto.setPassword("password123"); // 실제 비밀번호

        // 실행 & 검증
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isNoContent()); 
    }

    @Test
    @DisplayName("로그인 실패: 잘못된 비밀번호를 입력하면 401 Unauthorized를 반환한다")
    void loginFail() throws Exception {
        // 틀린 정보
        UserLoginDTO loginDto = new UserLoginDTO();
        loginDto.setLoginId("testuser");
        loginDto.setPassword("wrong_password");

        // 실행 & 검증
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized()); 
    }
}