package com.inspire.inspirebe.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inspire.inspirebe.user.entity.LocalCredentials;

@Repository
public interface AuthRepository extends JpaRepository<LocalCredentials, Long> {
}
