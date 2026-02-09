package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 아이디 중복 확인을 위한 메서드
     */
    boolean existsByLoginId(String loginId);

    /**
     * 로그인 등 아이디로 사용자 정보를 조회할 때 사용
     */
    Optional<UserEntity> findByLoginId(String loginId);

    /**
     * 사용자 정보 조회 시 출근 기록(attendances)을 함께 가져옴
     */
    @Query("""
            select u from UserEntity u
            join fetch u.attendances
            where u.id = :id
            """)
    Optional<UserEntity> findByIdWithAttendances(@Param("id") Long id);
} 