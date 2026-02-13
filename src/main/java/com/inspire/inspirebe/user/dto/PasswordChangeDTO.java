package com.inspire.inspirebe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "비밀번호 변경 요청 DTO")
public class PasswordChangeDTO {
    @Schema(
            description = "현재 비밀번호 (본인 확인용)",
            example = "OldPassword123!",
            minLength = 8,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String oldPassword;

    @Schema(
            description = "새 비밀번호 (8자 이상, 영문+숫자 조합 권장)",
            example = "NewPassword456!",
            minLength = 8,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String newPassword;
}
