package com.inspire.inspirebe.user.service;

import java.util.List;
import com.inspire.inspirebe.user.dto.UserResponseDTO;

/**
 * 관리자 전역 기능을 담당하는 서비스 인터페이스
 * 주요 역할: 회원 승인 관리(SUSPENDED -> ACTIVE), 전체 근무자 리스트 조회, 상세 정보 관리(CRUD)
 */
public interface AdminService {

    /**
     * 1. 회원가입 승인
     * @param id 승인할 유저의 고유 번호
     * 로직: UserStatus를 SUSPENDED에서 ACTIVE로 변경함 (시급은 상세페이지에서 별도 설정)
     */
    void approveUser(Long id);

    /**
     * 2. 전체 근무자 리스트 조회 (이미 승인된 인원 포함 전체)
     * @return 모든 유저 정보 리스트
     */
    List<UserResponseDTO> getAllUsers();

    /**
     * 3. 승인 대기 중인 유저 리스트 조회 
     * @return SUSPENDED(가입 직후 대기) 상태인 유저 정보 리스트
     * 설명: 관리자 메인 화면에서 승인이 필요한 신규 가입자 목록을 뿌려줄 때 사용함
     */
    List<UserResponseDTO> getSuspendedUsers();

    /**
     * 4. 근무자 상세 정보 조회
     * @param id 조회할 유저의 고유 번호
     * @return 특정 유저 한 명의 상세 데이터
     */
    // UserResponseDTO getUserDetail(Long id);

    /**
     * 5. 근무자 삭제 또는 퇴사 처리
     * @param id 삭제할 유저의 고유 번호
     */
    void deleteUser(Long id);
}