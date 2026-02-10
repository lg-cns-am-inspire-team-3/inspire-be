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
    @DisplayName("로그인 성공: 올바른 정보를 입력하면 204 No Content를 반환한다")
    void loginSuccess() throws Exception {
        UserLoginDTO loginDto = new UserLoginDTO();
        loginDto.setLoginId("testuser"); 
        loginDto.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isNoContent()); // 204 응답 확인
    }

    @Test
    @DisplayName("로그인 실패: 잘못된 정보를 입력하면 4xx 에러를 반환한다")
    void loginFail() throws Exception {
        UserLoginDTO loginDto = new UserLoginDTO();
        loginDto.setLoginId("wronguser");
        loginDto.setPassword("wrongpassword");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().is4xxClientError()); // 400 or 401 응답 확인
    }
}