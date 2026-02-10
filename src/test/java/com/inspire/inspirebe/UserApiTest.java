package com.inspire.inspirebe;

import com.inspire.inspirebe.user.entity.UserCredentials;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialsRepository credentialsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        UserEntity user1 = UserEntity.builder()
                .name("wooseong")
                .email("rymph0501@gmail.com")
                .role(UserRole.USER)
                .contact("010-1234-5678")
                .address("Seoul")
                .status(UserStatus.SUSPENDED)
                .build();

        userRepository.save(user1);
    }

    @Test
    void checkIdTest() throws Exception {
        // When & Then : user가 없는지 조회
        mockMvc.perform(get("/api/v1/users/check-id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        UserEntity user = userRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(user);


        System.out.format("User Entity : %s\n", user.toString());
    }

    @Test
    @DisplayName("회원가입(Create) 테스트")
    void createUserTest() throws Exception {
        String userJson = "{"
                + "\"loginId\":\"aaaa@aaaa\","
                + "\"password\":\"aaaa\","
                + "\"name\":\"aaaa\","
                + "\"email\":\"aaaa@aaaa\","
                + "\"contact\":\"010-1111-1111\""
                + "}";

        // when & then: 가입 요청 후 200 OK 또는 201 Created를 기대함
        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isOk()); // 컨트롤러가 ResponseEntity.ok()라면 isOk() 사용

        UserEntity user = userRepository.findById(2L).orElse(null);
        UserCredentials credentials = credentialsRepository.findByLoginId("aaaa@aaaa").orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(credentials);
        assertThat(passwordEncoder.matches("aaaa", credentials.getPasswordHash())).isEqualTo(true);
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
        assertThat(user.getStatus()).isEqualTo(UserStatus.SUSPENDED);
        assertThat(user.getEmail()).isEqualTo("aaaa@aaaa");

    }

    @Test
    @DisplayName("유저 정보 조회(Read) 테스트")
    void readUserTest() throws Exception {
        // given: setUp()에서 저장한 유저의 ID가 1L이라고 가정
        Long userId = 1L;

        // when & then: 유저 조회 API 호출 시 데이터가 잘 오는지 확인
        mockMvc.perform(get("/api/v1/users/{userId}", userId)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("wooseong")) // setUp에 입력된 이름
                .andExpect(jsonPath("$.email").value("rymph0501@gmail.com"));

        UserEntity user = userRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(user);

    }

    @Test
    void updateUserTest() throws Exception {
        // name, contact, email
        // Given : 다양한 JSON 문자열
        StringBuilder sb = new StringBuilder();
        String[] jsons = {
                "{\"name\":\"name1\"}",
                "{\"contact\":\"contact2\"}",
                "{\"email\":\"email3\"}",
                "{\"name\":\"name4\", \"contact\":\"null\"}",
                "{\"name\":\"name5\", \"email\":\"email5\"}",
                "{\"contact\":\"null\", \"email\":\"email6\"}",
                "{\"name\":\"name7\", \"contact\":\"content7\", \"email\":\"email7\"}",
                "{\"name\":\"name8\", \"contact\":\"null\", \"email\":\"email8\"}",
                "{}",
        };

        UserEntity user = userRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(user);
        sb.append("initial value").append("\n").append(user).append("\n");
        // name: wooseong, contact: 010-1234-5678, email: rymph0501@gmail.com

        // When & Then : 각 json에 대하여 update 요청
        for(String json : jsons) {
            mockMvc.perform(patch("/api/v1/users/{id}", 1L)
                    .contentType("application/json")
                    .content(json))
                    .andExpect(status().isNoContent());

            user = userRepository.findById(1L).orElse(null);
            Assertions.assertNotNull(user);
            sb.append(String.format("name: %s, contact: %s, email: %s\n", user.getName(), user.getContact(), user.getEmail()));
            // console output
            // name: name1, contact: 010-1234-5678, email: rymph0501@gmail.com
            // name: name1, contact: contact2, email: rymph0501@gmail.com
            // name: name1, contact: contact2, email: email3
            // name: name4, contact: null, email: email3
            // name: name5, contact: null, email: email5
            // name: name5, contact: null, email: email6
            // name: name7, contact: content7, email: email7
            // name: name8, contact: null, email: email8
            // name: name8, contact: null, email: email8
        }

        System.out.println(sb);
    }

    @Test
    void deleteUserTest() throws Exception {
        // When & Then : 유저 삭제
        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isNoContent());

        boolean exists = userRepository.existsById(1L);
        assertThat(exists).isEqualTo(false);
    }
    /*
    @PostMapping("")
    public ResponseEntity<String> signup(@RequestBody UserCreateDTO request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @GetMapping("/check-id/{loginId}")
    public ResponseEntity<Boolean> checkId(@PathVariable String loginId) {
        boolean isDuplicated = userService.isIdDuplicated(loginId);
        return ResponseEntity.ok(isDuplicated);
    }

    // --- 조장님이 추가하신 CRUD API ---

    // Read (단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id) {
        UserResponseDTO response = userService.getUser(id);
        return ResponseEntity.ok(response);
    }

    // Update (수정 - 임시)
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    // Delete (삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
     */
}
