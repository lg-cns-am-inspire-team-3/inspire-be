package com.inspire.inspirebe.attend.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.user.entity.UserEntity;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {

    Optional<Attend> findByUserAndWorkDate(UserEntity user, LocalDate workDate);
    
}
