package com.inspire.inspirebe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 승인 요청 DTO (관리자가 시급을 설정하여 사용자 계정을 승인합니다)")
public class UserApprovalRequest {
    @Schema(
            description = "사용자 시급 (원 단위)",
            example = "10000",
            minimum = "0"
    )
    private Integer salary; // 시급
}

// 관리자가 입력한 시급 데이터를 받아오는 바구니
