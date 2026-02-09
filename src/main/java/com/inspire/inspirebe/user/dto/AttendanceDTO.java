package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.attend.entity.Attend;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AttendanceDTO {
    private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private LocalDateTime totalTime;
    private Integer totalAmount;

    @Builder
    public AttendanceDTO(LocalDate workDate, LocalDateTime checkIn, LocalDateTime checkOut, LocalDateTime totalTime, Integer totalAmount) {
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalTime = totalTime;
        this.totalAmount = totalAmount;
    }


    public static AttendanceDTO from(Attend attend) {
        return AttendanceDTO.builder()
                .workDate(attend.getWorkDate())
                .checkIn(attend.getCheckIn())
                .checkOut(attend.getCheckOut())
                .totalTime(attend.getTotalTime())
                .build();
    }
}