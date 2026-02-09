package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 아이디 중복 확인을 위한 메서드
     * @param loginId 사용자가 입력한 아이디
     * @return 존재하면 true, 없으면 false
     */
    boolean existsByLoginId(String loginId);

    /**
     * 로그인 등 아이디로 사용자 정보를 조회할 때 사용
     */
    Optional<UserEntity> findByLoginId(String loginId);
}