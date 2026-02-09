package com.inspire.inspirebe.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private String id;
    private String loginId;
    private String email;
    private String name;
    private String contact;
    private String address;
    private List<AttendanceDTO> attendances;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class AttendanceDTO {
        private LocalDate workDate;
        private LocalDateTime checkIn;
        private LocalDateTime checkOut;
        private LocalDateTime totalTime;
        private Integer totalAmount;
    }
}
