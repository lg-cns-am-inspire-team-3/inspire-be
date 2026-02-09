package com.inspire.inspirebe.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private String loginId;
    private String password;
    private String name;
    private String contact;
    private String email;
}
