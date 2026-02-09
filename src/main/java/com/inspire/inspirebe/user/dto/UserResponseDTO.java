package com.inspire.inspirebe.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String loginId;
    private String email;
    private String name;
    private String contact;
    private String address;
    private List<AttendanceDTO> attendances;

    @Builder
    public UserResponseDTO(Long id, String loginId, String email, String name, String contact, String address, List<AttendanceDTO> attendances) {
        this.id = id;
        this.loginId = loginId;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.attendances = attendances;
    }
}
