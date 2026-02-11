package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * 1. 관리자 승인 대기 또는 특정 상태의 사용자 목록 조회
     * UserStatus(SUSPENDED, ACTIVE 등)를 파라미터로 받아 해당되는 유저 리스트를 반환합니다.
     */
    List<UserEntity> findByStatus(UserStatus status);
} 