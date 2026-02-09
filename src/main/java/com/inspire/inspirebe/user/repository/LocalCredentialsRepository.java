package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.LocalCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalCredentialsRepository extends JpaRepository<LocalCredentials, Long> {
    
}
