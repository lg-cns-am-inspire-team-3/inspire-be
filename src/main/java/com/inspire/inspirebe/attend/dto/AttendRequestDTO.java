package com.inspire.inspirebe.attend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "출퇴근 요청 DTO")
public class AttendRequestDTO {

    @Schema(description = "QR 인증 토큰 값", example = "ATTENDANCE_QR_TEST_OKOK_LGCNS")
    private String qrToken;

}
