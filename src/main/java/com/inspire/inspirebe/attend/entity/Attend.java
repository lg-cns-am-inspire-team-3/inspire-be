package com.inspire.inspirebe.attend.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import com.inspire.inspirebe.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration; // [추가] 시간 계산을 위해 필요

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

    /**
     * [수정/추가] DTO와의 매핑 및 급여 계산을 위해 
     * 근무 시간을 '분' 단위(Integer)로 저장하는 필드를 추가합니다.
     */
    @Column(name = "work_minute")
    private Integer workMinute;

    // 기존 totalTime은 LocalDateTime 타입이라 계산에 부적합하여 
    // 위 workMinute로 대체하거나 보조용으로 유지합니다.

    public Attend(UserEntity user) {
        this.user = user;
        this.workDate = LocalDate.now();
        this.checkIn = LocalDateTime.now();
    }

    /**
     * [수정] 퇴근 시간을 업데이트할 때 
     * 자동으로 근무 시간(분)을 계산하여 저장하는 로직을 추가합니다.
     */
    public void updateCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
        
        if (this.checkIn != null && this.checkOut != null) {
            // 출근 시간과 퇴근 시간의 차이를 구해서 분 단위로 변환
            long minutes = Duration.between(this.checkIn, this.checkOut).toMinutes();
            this.workMinute = (int) minutes;
        }
    }
}