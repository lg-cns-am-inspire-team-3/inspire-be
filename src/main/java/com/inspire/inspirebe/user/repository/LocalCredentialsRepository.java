package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.LocalCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalCredentialsRepository extends JpaRepository<LocalCredentials, Long> {
    Optional<LocalCredentials> findByUserId(Long userId);
}
