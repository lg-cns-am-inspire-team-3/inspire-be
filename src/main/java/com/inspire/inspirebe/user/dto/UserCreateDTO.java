package com.inspire.inspirebe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "일반 회원가입 요청 DTO")
public class UserCreateDTO {
    
    @Schema(
            description = "로그인 아이디 (영문/숫자 4~20자)",
            example = "seungjun123",
            minLength = 4,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String loginId;

    @Schema(
            description = "비밀번호 (8자 이상, 영문+숫자 조합 권장)",
            example = "Password123!",
            minLength = 8,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;

    @Schema(
            description = "사용자 이름",
            example = "홍길동",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "연락처 (하이픈 포함 가능)",
            example = "010-1234-5678",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String contact;

    @Schema(
            description = "이메일 주소",
            example = "test@email.com",
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;
}
