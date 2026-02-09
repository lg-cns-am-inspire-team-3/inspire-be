package com.inspire.inspirebe.auth.repository;

import com.inspire.inspirebe.user.entity.UserEntity; // 👈 엔티티 임포트
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {

}