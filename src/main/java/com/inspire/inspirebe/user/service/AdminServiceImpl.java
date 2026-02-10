package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.mapper.UserEntityMapper;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    /**
     * 1. 회원가입 승인
     * 시급 입력 없이 상태만 ACTIVE로 변경합니다.
     */
    @Override
    @Transactional
    public void approveUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 사용자를 찾을 수 없습니다."));

        user.activeUser(); // 상태를 ACTIVE로 변경
    }

    /**
     * 2. 전체 근무자 리스트 조회
     * AdminService 인터페이스의 약속을 이행합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserEntityMapper::toResponse)
                .toList();
    }

    /**
     * 3. 승인 대기 중인 유저 리스트 조회
     * AdminService 인터페이스의 getSuspendedUsers() 약속을 이행합니다.
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
     * 4. 근무자 상세 정보 조회
     */
    // @Override
    // @Transactional(readOnly = true)
    // public UserResponseDTO getUserDetail(Long id) {
    //     UserEntity user = userRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 근무자를 찾을 수 없습니다."));
        
    //     return UserEntityMapper.toResponse(user); 
    // }

    /**
     * 5. 근무자 삭제
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 사용자를 찾을 수 없습니다."));
        
        userRepository.delete(user);
    }
}