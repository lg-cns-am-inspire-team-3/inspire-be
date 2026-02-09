package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.entity.UserCredentials;
import com.inspire.inspirebe.user.mapper.UserEntityMapper;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
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
    private final UserCredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    // --- 회원가입 기능 ---
    @Override
    @Transactional
    public void signup(UserCreateDTO request) {

        // 1. 아이디 중복 체크
        if(isIdDuplicated(request.getLoginId())) {
            throw new EntityNotFoundException("이미 사용 중인 아이디입니다.");
        }

        // 2. User 엔티티 빌드 & 저장
        UserEntity user = UserEntityMapper.fromUserCreate(request);
        userRepository.save(user);

        // 3. UserCredentials 엔티티 빌드 & 저장
        UserCredentials credentials = UserCredentials.builder()
                .userId(user.getId())
                .loginId(request.getLoginId())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();
        credentialsRepository.save(credentials);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isIdDuplicated(String loginId) {
        return credentialsRepository.existsByLoginId(loginId);
    }

    // --- 조장님이 추가하신 CRUD 기능들 (껍데기 유지) ---

    // Read
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        UserEntity userEntity = userRepository.findByIdWithAttendances(id)
                .orElseThrow(EntityNotFoundException::new);

        return UserEntityMapper.toResponse(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public String getUserRole(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return userEntity.getRole().name();
    }

    // Updates
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        userUpdateDTO.getName().ifPresent(userEntity::changeName);
        userUpdateDTO.getEmail().ifPresent(userEntity::changeEmail);
        userUpdateDTO.getContact().ifPresent(userEntity::changeContact);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        UserCredentials credentials = credentialsRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        String oldHash = credentials.getPasswordHash();
        if(!passwordEncoder.matches(oldPassword, oldHash)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        credentials.changePasswordHash(passwordEncoder.encode(newPassword));
    }

    // Delete
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(userEntity);
    }

    /*
     *
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Long validateCredentials(String loginId, String password) {
        UserCredentials credentials = credentialsRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("유저 정보가 없습니다."));
        if(!passwordEncoder.matches(password, credentials.getPasswordHash())) {
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }
        return credentials.getUserId();
    }
} // 클래스 끝