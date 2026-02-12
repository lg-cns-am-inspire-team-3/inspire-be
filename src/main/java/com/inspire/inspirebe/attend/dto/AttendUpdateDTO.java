package com.inspire.inspirebe.attend.dto;

import com.inspire.inspirebe.binding.Update;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AttendUpdateDTO {
    Update<LocalDate> workDate = Update.absent();
    Update<LocalDateTime> checkIn = Update.absent();
    Update<LocalDateTime> checkOut = Update.absent();
}
