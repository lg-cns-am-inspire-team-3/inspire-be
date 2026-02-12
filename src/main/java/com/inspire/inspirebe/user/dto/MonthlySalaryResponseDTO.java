package com.inspire.inspirebe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "월별 급여 합계 응답 DTO")
public class MonthlySalaryResponseDTO {
    @Schema(
            description = "해당 월의 총 예상 급여 금액 (원 단위, 세전 기준)",
            example = "2400000",
            minimum = "0"
    )
    private Integer totalAmount;
}
