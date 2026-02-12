package com.inspire.inspirebe.attend.repository;

import com.inspire.inspirebe.attend.entity.Attend;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendRepository extends JpaRepository<Attend, Long>, JpaSpecificationExecutor<Attend> {
    @Query("SELECT a FROM Attend a JOIN FETCH a.user WHERE a.user.id = :userId ORDER BY a.workDate ")
    List<Attend> findWithUserId(@Param("userId") Long userId);
    @Query("SELECT a FROM Attend a JOIN FETCH a.user WHERE a.workDate BETWEEN :start AND :end")
    List<Attend> findAllByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Override
    @EntityGraph(attributePaths = {"user"})
    List<Attend> findAll(Specification<Attend> spec, Sort sort);
    Optional<Attend> findByUserId(Long userId);
}