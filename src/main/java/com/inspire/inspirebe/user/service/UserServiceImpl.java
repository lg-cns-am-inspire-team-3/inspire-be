package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.entity.LocalCredentials;
import com.inspire.inspirebe.user.mapper.UserEntityMapper;
import com.inspire.inspirebe.user.repository.LocalCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public void signup(UserCreateDTO request) {
        // 1. 아이디 중복 체크
        if (isIdDuplicated(request.getLoginId())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        // 2. 엔티티 빌드
        UserEntity user = UserEntityMapper.fromUserCreate(request);

        LocalCredentials credentials = LocalCredentials.builder()
                .userId(user.getId())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        // 3. 저장
        userRepository.save(user);
        credentialsRepository.save(credentials);
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
        UserEntity userEntity = userRepository.findByIdWithAttendances(id)
                .orElseThrow(EntityNotFoundException::new);

        return UserEntityMapper.toResponse(userEntity);
    }

    // Updates
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userUpdateDTO.getPassword().ifPresent(password -> {
            LocalCredentials localCredentials = credentialsRepository.findByUserId(id)
                    .orElseThrow(EntityNotFoundException::new);
            localCredentials.changePasswordHash(passwordEncoder.encode(password));
        });

        userUpdateDTO.getName().ifPresent(userEntity::changeName);
        userUpdateDTO.getEmail().ifPresent(userEntity::changeEmail);
        userUpdateDTO.getContact().ifPresent(userEntity::changeContact);

    }

    // Delete
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(userEntity);
    }
} // 클래스 끝