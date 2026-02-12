package com.inspire.inspirebe.attend.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import com.inspire.inspirebe.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Attend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "work_minute")
    private Integer workMinute;

    @Builder
    public Attend(Long id, UserEntity user, LocalDate workDate, LocalDateTime checkIn, LocalDateTime checkOut, Integer workMinute) {
        this.id = id;
        this.user = user;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.workMinute = workMinute;
    }

    @PrePersist
    public void prePersist() {
        if(workDate == null) {
            workDate = LocalDate.now();
        }

        if(checkIn == null) {
            checkIn = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if(checkOut == null) {
            workMinute = null;
        }

        if(checkIn != null) {
            workMinute = (int) Duration.between(checkIn, checkOut).toMinutes();
        }
    }

    public void changeWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }
    public void changeCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }
    public void changeCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public void checkOut() {
        checkOut = LocalDateTime.now();
        workMinute = (int) Duration.between(checkIn, checkOut).toMinutes();
    }

    public boolean calculatable() {
        return workMinute != null;
    }
}
