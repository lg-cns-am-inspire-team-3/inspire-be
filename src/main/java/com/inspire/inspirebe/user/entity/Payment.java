package com.inspire.inspirebe.user.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "period_starty")
    private LocalDate periodStart;
    @Column(name = "period_end")
    private LocalDate periodEnd;

    @Column(name = "total_minutes")
    private Integer totalMinutes;

    @Column(name = "calculated_pay")
    private Integer calculatedPay;
}
