package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserSignupRequest;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
}