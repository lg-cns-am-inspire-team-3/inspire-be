package com.inspire.inspirebe.attend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "출퇴근 기록 응답 DTO")
public class AttendResponseDTO {
    @Schema(description = "출퇴근 기록 ID", example = "15")
    private Long id;

    @Schema(description = "사용자 ID", example = "3")
    private Long userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "근무 날짜", example = "2026-02-12")
    private LocalDate workDate;

    @Schema(
            description = "출근 시간",
            example = "07:30:00"
    )
    private LocalTime checkIn;

    @Schema(
            description = "퇴근 시간 (퇴근 전에는 null 가능)",
            example = "17:30:00",
            nullable = true
    )
    private LocalTime checkOut;

    @Schema(
            description = "해당 근무에 대한 예상 지급 급여 (원 단위)",
            example = "80000"
    )
    private Integer wage;

    @Builder
    public AttendResponseDTO(Long id, Long userId, String userName, LocalDate workDate, LocalTime checkIn, LocalTime checkOut, Integer wage){
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.wage = wage;
    }
}
