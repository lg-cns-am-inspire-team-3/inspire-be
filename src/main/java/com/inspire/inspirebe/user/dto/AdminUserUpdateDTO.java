package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.binding.Update;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 관리자는 추가로 수정할 수 있음
 */
@NoArgsConstructor
@Getter
@Setter
public class AdminUserUpdateDTO extends UserUpdateDTO{
    // 시급
    private Update<Integer> salary = Update.absent();
    // 상태
    private Update<String> status = Update.absent();
}
