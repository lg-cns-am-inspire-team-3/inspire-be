package com.inspire.inspirebe.attend.repository;

import com.inspire.inspirebe.attend.entity.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendRepository extends JpaRepository<Attend, Long> {

    List<Attend> findByWorkDateBetween(LocalDate start, LocalDate end);
}

