package com.inspire.inspirebe.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 사용자 정보 조회 시 출근 기록(attendances)을 함께 가져옴
     */
    @Query("""
            select u from UserEntity u
            join fetch u.attendances
            where u.id = :id
            """)
    Optional<UserEntity> findByIdWithAttendances(@Param("id") Long id);

    /**
     * 1. 관리자 승인 대기 또는 특정 상태의 사용자 목록 조회
     * UserStatus(SUSPENDED, ACTIVE 등)를 파라미터로 받아 해당되는 유저 리스트를 반환합니다.
     */
    List<UserEntity> findByStatus(UserStatus status);

} 