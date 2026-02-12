package com.inspire.inspirebe.attend.mapper;

import com.inspire.inspirebe.attend.dto.AttendResponseDTO;
import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.entity.UserEntity;

public class AttendEntityMapper {

    public static AttendResponseDTO toResponse(Attend attend) {
        return AttendResponseDTO.builder()
                .id(attend.getId())
                .userId(attend.getUser().getId())
                .userName(attend.getUser().getName())
                .workDate(attend.getWorkDate())
                .checkIn(attend.getCheckIn().toLocalTime())
                .checkOut(attend.getCheckOut().toLocalTime())
                .wage(attend.getWorkMinute() * attend.getUser().getSalary() / 60)
                .build();
    }
}
