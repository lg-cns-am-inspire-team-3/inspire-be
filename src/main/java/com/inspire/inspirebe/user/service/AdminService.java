package com.inspire.inspirebe.user.service;

import java.util.List;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO; // 추가 확인

/**
 * 관리자 전역 기능을 담당하는 서비스 인터페이스
 */
public interface AdminService {

    /** 1. 회원가입 승인 */
    void approveUser(Long id);

    /** 2. 전체 근무자 리스트 조회 */
    List<UserResponseDTO> getAllUsers();

    /** 3. 승인 대기 중인 유저 리스트 조회 */
    List<UserResponseDTO> getSuspendedUsers();

    /** 4. 근무자 상세 정보 조회 */
    UserResponseDTO getUserDetail(Long id);

    /** 5. 근무자 삭제 또는 퇴사 처리 */
    void deleteUser(Long id);

    /** 6. 근무자 정보 수정 */
    void updateUser(Long id, UserUpdateDTO request);
}