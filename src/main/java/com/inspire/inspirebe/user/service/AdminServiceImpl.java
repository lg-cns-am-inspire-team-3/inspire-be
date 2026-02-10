package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.mapper.UserEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    @Transactional // 데이터 수정을 위해 반드시 필요합니다!
    public void approveUser(Long id, Integer salary) {
        // 1. 해당 유저가 있는지 확인
        UserEntity user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        // 2. 시급 설정 및 상태 변경 (UserStatus Enum 사용 가정)
        user.changeSalary(salary);
        user.activeUser();

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserEntityMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
        
        userRepository.delete(user);
    }
}
