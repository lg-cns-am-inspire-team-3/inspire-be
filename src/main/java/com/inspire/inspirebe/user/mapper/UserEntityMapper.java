package com.inspire.inspirebe.user.mapper;

import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.user.dto.AttendanceDTO;
import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.entity.enums.UserStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserEntityMapper {
    public static UserEntity fromUserCreate(UserCreateDTO userCreateDTO) {
        return UserEntity.builder()
                .email(userCreateDTO.getEmail())
                .name(userCreateDTO.getName())
                .contact(userCreateDTO.getContact())
                .build();
    }

    public static UserResponseDTO toResponse(UserEntity userEntity) {
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .salary(userEntity.getSalary())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .contact(userEntity.getContact())
                .address(userEntity.getAddress())
                .build();
    }
}
