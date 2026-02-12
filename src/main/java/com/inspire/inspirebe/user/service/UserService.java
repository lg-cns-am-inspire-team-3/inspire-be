package com.inspire.inspirebe.user.service;

// 1. 모든 import 문은 파일 최상단에 모아야 합니다.
import com.inspire.inspirebe.admin.dto.AdminUserUpdateDTO;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 2. 인터페이스 선언은 한 번만 합니다.
public interface UserService {

    /**
     * 회원가입을 처리하는 메서드입니다.
     */
    void signup(UserCreateDTO request);

    /**
     * 아이디가 이미 존재하는지 확인하는 메서드입니다.
     */
    boolean isIdDuplicated(String loginId);

    List<UserResponseDTO> getAllUsers(String statusStr, String roleStr);

    // --- 조장님이 추가하신 CRUD 메서드들을 하나의 중괄호 안으로 합칩니다 ---
    // Read
    UserResponseDTO getUser(Long id);

    String getUserRole(Long id);

    // Update
    void updateUser(Long id, UserUpdateDTO userUpdateDTO);

    void updateUserByAdmin(Long id, AdminUserUpdateDTO userUpdateDTO);

    void updatePassword(Long id, String oldPassword, String newPassword);

    List<UserResponseDTO> getSuspendedUsers();

    // Delete
    void deleteUser(Long id);

    Long validateCredentials(String loginId, String password);

    UserEntity getReferenceBy(Long id);
}