package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.binding.Update;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    private Update<String> password;
    private Update<String> name;
    private Update<String> contact;
    private Update<String> email;
}