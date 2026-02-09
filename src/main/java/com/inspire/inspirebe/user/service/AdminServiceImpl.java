package com.inspire.inspirebe.user.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import com.inspire.inspirebe.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    @Transactional // 데이터 수정을 위해 반드시 필요합니다!
    public void approveUser(Long id, Integer salary) {
        // 1. 해당 유저가 있는지 확인
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 2. 시급 설정 및 상태 변경 (UserStatus Enum 사용 가정)
        // UserEntity에 setSalary(), setStatus() 메서드가 있어야 합니다.
        user.setSalary(salary);
        user.setStatus(UserStatus.ACTIVE); 
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
