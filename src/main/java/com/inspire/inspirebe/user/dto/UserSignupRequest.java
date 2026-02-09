package com.inspire.inspirebe.user.dto;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UserSignupRequest {
    private String loginId;  // 사용자가 입력한 ID
    private String name;     // 이름
    private String email;    // 이메일
    private String contact;  // 전화번호
}