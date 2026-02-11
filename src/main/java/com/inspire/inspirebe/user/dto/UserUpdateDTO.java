package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.binding.Update;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * user가 수정할 수 있는 필드
 */
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    // 이름
    private Update<String> name = Update.absent();
    // 연락처
    private Update<String> contact = Update.absent();
    // 이메일
    private Update<String> email = Update.absent();
    // 거주지 주소
    private Update<String> address = Update.absent();
}