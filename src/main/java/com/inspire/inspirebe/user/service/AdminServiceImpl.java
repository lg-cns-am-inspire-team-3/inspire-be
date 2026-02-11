package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
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
     */
    @Override
    @Transactional
    public void approveUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 사용자를 찾을 수 없습니다."));

        user.activateUser();
    }

    /**
     * 2. 전체 근무자 리스트 조회
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
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserDetail(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 근무자를 찾을 수 없습니다."));
        
        return UserEntityMapper.toResponse(user); 
    }

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

    /**
     * 6. 근무자 정보 수정
     */
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 근무자를 찾을 수 없습니다."));

        // Update<T> 바인딩을 활용한 선택적 업데이트
        request.getName().ifPresent(user::changeName);
        request.getContact().ifPresent(user::changeContact);
        request.getEmail().ifPresent(user::changeEmail);
        request.getAddress().ifPresent(user::changeAddress);
        // 시급 변경 (Entity에 구현된 changeSalary 메서드 호출)
        request.getSalary().ifPresent(user::changeSalary);
    }
} // 클래스 닫는 중괄호