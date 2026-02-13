package com.inspire.inspirebe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "관리자 출근 현황 조회 응답 DTO")
public class AdminAttendanceResponseDTO {

    @Schema(
            description = "근무자 이름",
            example = "홍길동"
    )
    private String name;

    @Schema(
            description = "출근 시간 (HH:mm 형식, 출근 전에는 null 가능)",
            example = "09:02",
            nullable = true
    )
    private String checkIn;

    @Schema(
            description = "퇴근 시간 (HH:mm 형식, 퇴근 전에는 null 가능)",
            example = "18:05",
            nullable = true
    )
    private String checkOut;

    @Schema(
            description = "해당 월의 예상 총 급여 (원 단위)",
            example = "2400000"
    )
    private Integer monthlyPay;
}
/*
    private Long userId;
    private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer workMinute;
 */
