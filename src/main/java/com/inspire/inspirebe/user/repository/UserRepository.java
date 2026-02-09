package com.inspire.inspirebe.user.repository;

import com.inspire.inspirebe.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
                select u from UserEntity u
                join fetch u.attendances
                where u.id = :id
            """)
    Optional<UserEntity> findByIdWithPayments(@Param("id") Long id);
}
