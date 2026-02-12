package com.inspire.inspirebe.attend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AttendResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer wage;

    @Builder
    public AttendResponseDTO(Long id, Long userId, String userName, LocalDate workDate, LocalDateTime checkIn, LocalDateTime checkOut, Integer wage){
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.wage = wage;
    }
}
