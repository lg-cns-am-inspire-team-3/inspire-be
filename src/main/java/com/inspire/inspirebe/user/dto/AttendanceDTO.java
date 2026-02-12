package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.attend.entity.Attend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "출결 정보 응답 데이터") //
public class AttendanceDTO {

    @Schema(description = "사용자 고유 ID", example = "1") //
    private Long userId;

    @Schema(description = "근무 날짜", example = "2026-02-12") //
    private LocalDate workDate;

    @Schema(description = "출근 시간", example = "2026-02-12T09:00:00") //
    private LocalDateTime checkIn;

    @Schema(description = "퇴근 시간", example = "2026-02-12T18:00:00") //
    private LocalDateTime checkOut;

    @Schema(description = "시급 또는 정산 금액", example = "10500") //
    private Integer wage;

    @Builder
    public AttendanceDTO(Long userId, LocalDate workDate, LocalDateTime checkIn, LocalDateTime checkOut, Integer wage) {
        this.userId = userId;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.wage = wage;
    }

    public static AttendanceDTO from(Attend attend) {
        return AttendanceDTO.builder()
                .userId(attend.getUser().getId())
                .workDate(attend.getWorkDate())
                .checkIn(attend.getCheckIn())
                .checkOut(attend.getCheckOut())
                .wage(attend.getWorkMinute()) 
                .build();
    }
}