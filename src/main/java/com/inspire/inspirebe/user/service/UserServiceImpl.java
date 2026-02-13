package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.admin.dto.AdminUserUpdateDTO;
import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.attend.service.AttendService;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.entity.UserCredentials;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import com.inspire.inspirebe.user.mapper.UserEntityMapper;
import com.inspire.inspirebe.user.repository.UserCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import com.inspire.inspirebe.user.specification.UserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Dependency Injection
    private final UserRepository userRepository;
    private final UserCredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 1. 회원가입 요청
     */
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

    /**
     * 2. 회원 중복 확인
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isIdDuplicated(String loginId) {
        return credentialsRepository.existsByLoginId(loginId);
    }

    /**
     * 3. 모든 유저 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers(String statusStr, String roleStr) {
        UserStatus status = statusStr != null ? UserStatus.valueOf(statusStr.toUpperCase()) : null;
        UserRole role = roleStr != null ? UserRole.valueOf(roleStr.toUpperCase()) : null;

        Specification<UserEntity> spec = Specification
                .where(UserSpecification.hasStatus(status))
                .and(UserSpecification.hasRole(role));

        return userRepository.findAll(spec)
                .stream()
                .map(UserEntityMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 4. 특정 유저 조회
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 근무자를 찾을 수 없습니다."));

        return UserEntityMapper.toResponse(user);
    }

    /**
     * 5. 승인 대기 유저 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getSuspendedUsers() {
        return userRepository.findByStatus(UserStatus.SUSPENDED)
                .stream()
                .map(UserEntityMapper::toResponse)
                .toList();
    }


    /**
     * 6. 특정 유저 삭제
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
    }

    /**
     * 7. 유저 Role 조회
     *    Jwt access token 만들 때 사용
     */
    @Override
    @Transactional(readOnly = true)
    public String getUserRole(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return userEntity.getRole().name();
    }

    // Updates
    /**
     * 8. 공통 업데이트 메소드
     */
    public void applyCommonUpdate(UserEntity userEntity, UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.getName().ifPresent(userEntity::changeName);
        userUpdateDTO.getEmail().ifPresent(userEntity::changeEmail);
        userUpdateDTO.getContact().ifPresent(userEntity::changeContact);
        userUpdateDTO.getAddress().ifPresent(userEntity::changeAddress);
    }
    /**
     * 9. 특정 유저 업데이트 (유저용)
     */
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        applyCommonUpdate(userEntity, userUpdateDTO);
    }

    /**
     * 10. 특정 유저 업데이트 (관리자용)
     */
    @Override
    @Transactional
    public void updateUserByAdmin(Long id, AdminUserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));

        applyCommonUpdate(userEntity, userUpdateDTO);
        userUpdateDTO.getSalary().ifPresent(userEntity::changeSalary);
        userUpdateDTO.getStatus().ifPresent(userEntity::changeUserStatus);
    }

    /**
     * 11. 유저 비밀번호 수정
     *    아직 구현 안함
     */
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

    /**
     * 12. 비밀번호 검증
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