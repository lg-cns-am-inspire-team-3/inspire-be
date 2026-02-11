package com.inspire.inspirebe.common.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 엔티티를 못 찾았을 때 (아이디 없음 등)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("EntityNotFoundException : {}", e.getMessage());
        // 소정님의 ErrorResponse 규격에 맞춰서 반환!
        return ResponseEntity.status(404).body(new ErrorResponse(404, "NOT_FOUND", e.getMessage()));
    }

    // 2. 비번 틀림 등 잘못된 인자 예외 처리 (추가 필수!)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponse(400, "BAD_REQUEST", e.getMessage()));
    }
}