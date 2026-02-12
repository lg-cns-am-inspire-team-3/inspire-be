package com.inspire.inspirebe.user.mapper;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.entity.UserEntity;

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
