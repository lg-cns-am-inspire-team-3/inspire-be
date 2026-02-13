package com.inspire.inspirebe.attend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "출결 정보 응답 데이터")
public class AttendResponseDTO {
    @Schema(description = "사용자 고유 ID", example = "1") //
    private Long id;
    @Schema(description = "사용자 고유 ID", example = "1") //
    private Long userId;
    @Schema(description = "사용자 이름", example = "김이박") //
    private String userName;
    @Schema(description = "근무 날짜", example = "2026-02-12") //
    private LocalDate workDate;
    @Schema(description = "출근 시간", example = "07:30") //
    private LocalTime checkIn;
    @Schema(description = "퇴근 시간", example = "17:30") //
    private LocalTime checkOut;
    @Schema(description = "정산 금액", example = "100000") //
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
