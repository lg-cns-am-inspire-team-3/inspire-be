package com.inspire.inspirebe;

import com.inspire.inspirebe.user.dto.UserApprovalRequest;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JsonMapper jsonMapper;
    private UserCredentialsRepository credentialsRepository;

    @BeforeEach
    /*
     * 초기 DB 상태 설정
     */
    void setUp() {
        userRepository.deleteAll();

        UserEntity user1 = UserEntity.builder()
                .name("wooseong")
                .email("rymph0501@gmail.com")
                .role(UserRole.USER)
                .contact("010-1234-5678")
                .address("Seoul")
                .attendances(Collections.emptyList())
                .status(UserStatus.SUSPENDED)
                .build();

        UserEntity user2 = UserEntity.builder()
                .name("hyebin")
                .email("hyebin@gmail.com")
                .role(UserRole.ADMIN)
                .contact("010-4321-8765")
                .address("Seoul")
                .attendances(Collections.emptyList())
                .status(UserStatus.ACTIVE)
                .build();

        UserEntity user3 = UserEntity.builder()
                .name("hakbin")
                .email("hakbin@gmail.com")
                .role(UserRole.USER)
                .contact("010-5678-1234")
                .address("Seoul")
                .attendances(Collections.emptyList())
                .status(UserStatus.SUSPENDED)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    // user 회원가입 승인
    void approveUserTest() throws Exception {
        // Given : request 정의
        Long userId = 1L; // 테스트용 ID
        UserApprovalRequest request = new UserApprovalRequest(10000);

        // When & Then : 회원 승인
        mockMvc.perform(patch("/api/v1/admin/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("회원 승인이 완료되었습니다."))
                .andDo(print());

        UserEntity user = userRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(user);
        assertThat(user.getSalary()).isEqualTo(10000);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

        System.out.println(user);
    }

    @Test
    // 모든 user 조회
    void getAllUsersTest() throws Exception {
        // When & Then : 모든 user 조회
        mockMvc.perform(get("/api/v1/admin/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // 응답이 리스트 형태인지 확인 (JSON Path 사용)
                .andExpect(jsonPath("$").isArray()) 
                .andDo(print());

        List<UserEntity> users = userRepository.findAll();
        assertThat(users)
                .hasSize(3)
                .extracting(UserEntity::getName)
                .containsExactlyInAnyOrder(
                        "wooseong",
                        "hyebin",
                        "hakbin"
                );

        users.forEach(System.out::println);
    }
}
