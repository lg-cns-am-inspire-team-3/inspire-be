package com.inspire.inspirebe.attend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inspire.inspirebe.attend.entity.Attend;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {
}
