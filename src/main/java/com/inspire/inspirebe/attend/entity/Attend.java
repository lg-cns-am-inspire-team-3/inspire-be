package com.inspire.inspirebe.attend.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import com.inspire.inspirebe.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "total_time")
    private LocalDateTime totalTime;

    public Attend(UserEntity user) {
        this.user = user;
        this.workDate = LocalDate.now();
        this.checkIn = LocalDateTime.now();
    }

    public void updateCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    

}
