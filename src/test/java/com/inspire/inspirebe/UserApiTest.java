package com.inspire.inspirebe;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialsRepository credentialsRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .name("wooseong")
                .email("rymph0501@gmail.com")
                .role(UserRole.USER)
                .contact("010-1234-5678")
                .attendances(Collections.emptyList())
                .status(UserStatus.SUSPENDED)
                .build();

        userRepository.save(user1);
    }

    @Test
    void checkIdTest() {

    }

    @Test
    void createUserTest() {

    }

    @Test
    void readUserTest() {

    }

    @Test
    void updateUserTest() {

    }

    @Test
    void deleteUserTest() {

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
