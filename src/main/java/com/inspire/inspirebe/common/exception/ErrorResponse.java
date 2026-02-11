package com.inspire.inspirebe.common.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * 프론트엔드와 공유하는 공통 에러 응답 규격
 */
@Getter
@Builder
public class ErrorResponse {
    private final int status;    // HTTP 상태 코드
    private final String code;   // 비즈니스 에러 코드
    private final String message; // 사용자에게 노출할 에러 메시지
}