package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserSignupRequest;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.repository.LocalCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // 1. 모든 의존성 주입을 클래스 최상단으로 모았습니다.
    private final UserRepository userRepository;
    private final LocalCredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    // --- 회원가입 기능 ---
    @Override
    @Transactional
    public void signup(UserSignupRequest request) {
        // 1. 아이디 중복 체크
        if (isIdDuplicated(request.getLoginId())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        // 2. 엔티티 빌드 (비밀번호 제외)
        UserEntity user = UserEntity.builder()
                .loginId(request.getLoginId())
                .name(request.getName())
                .email(request.getEmail())
                .contact(request.getContact())
                .role(UserRole.USER) 
                .build();

        // 3. 저장
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isIdDuplicated(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    // --- 조장님이 추가하신 CRUD 기능들 (껍데기 유지) ---
    
    // Create
    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        return null;
    }

    // Read
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        return null;
    }

    // Updates
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        // 구현 필요
    }

    // Delete
    @Override
    @Transactional
    public void deleteUser(Long id) {
        // 구현 필요
    }
} // 클래스 끝