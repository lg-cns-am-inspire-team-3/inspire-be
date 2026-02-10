package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.binding.Update;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    private Update<String> name = Update.absent();
    private Update<String> contact = Update.absent();
    private Update<String> email = Update.absent();
}