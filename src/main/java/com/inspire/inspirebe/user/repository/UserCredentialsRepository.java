package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    boolean existsByLoginId(String loginId);
    // user id로 credentials 찾기
    Optional<UserCredentials> findByUserId(Long id);

    Optional<UserCredentials> findByLoginId(String loginId);
}
