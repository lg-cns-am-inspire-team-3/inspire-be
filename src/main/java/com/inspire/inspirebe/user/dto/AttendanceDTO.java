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
    private Long userId;
    private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer workMinute;

    @Builder
    public AttendanceDTO(Long userId, LocalDate workDate, LocalDateTime checkIn, LocalDateTime checkOut, Integer workMinute) {
        this.userId = userId;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.workMinute = workMinute;
    }


    public static AttendanceDTO from(Attend attend) {
        return AttendanceDTO.builder()
                .userId(attend.getUser().getId())
                .workDate(attend.getWorkDate())
                .checkIn(attend.getCheckIn())
                .checkOut(attend.getCheckOut())
                .workMinute(attend.getWorkMinute())
                .build();
    }
}