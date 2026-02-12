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
    private String email;
    private String name;
    private String contact;
    private String address;
    private Integer salary;

    @Builder
    public UserResponseDTO(Long id, String email, String name, String contact, String address, Integer salary) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.salary = salary;
    }
}
