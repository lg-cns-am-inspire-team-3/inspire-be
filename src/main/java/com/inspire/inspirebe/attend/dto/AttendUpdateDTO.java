package com.inspire.inspirebe.attend.dto;

import com.inspire.inspirebe.binding.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "출결 정보 수정 요청 데이터")
public class AttendUpdateDTO {

    @Schema(description = "근무 날짜", type = "string", implementation = String.class, example = "2026-02-12")
    private Update<LocalDate> workDate = Update.absent();

    @Schema(description = "출근 시간", type = "string", implementation = String.class, example = "2026-02-12T09:00:00")
    private Update<LocalDateTime> checkIn = Update.absent();

    @Schema(description = "퇴근 시간", type = "string", implementation = String.class, example = "2026-02-12T18:00:00")
    private Update<LocalDateTime> checkOut = Update.absent();
}