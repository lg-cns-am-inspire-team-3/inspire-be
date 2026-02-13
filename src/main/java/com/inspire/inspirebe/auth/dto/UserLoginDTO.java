package com.inspire.inspirebe.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "로그인 요청 데이터")
public class UserLoginDTO { 
    @Schema(description = "사용자 로그인 아이디", example = "test123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String loginId;
    @Schema(description = "사용자 비밀번호", example = "password123!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
