package com.inspire.inspirebe.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminAttendanceResponseDTO {

    private String name;
    private String checkIn;   // 시간만 문자열로
    private String checkOut;
    private Integer monthlyPay;  // 이번달 예상 급여
}
/*
    private Long userId;
    private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer workMinute;
 */
