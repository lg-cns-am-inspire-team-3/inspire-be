package com.inspire.inspirebe.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "토큰 응답 데이터 (Access Token 및 만료 정보)") 
public class TokenResponseDTO {

    @Schema(description = "발급된 Access Token (Bearer 타입)", 
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYWtiaW4xMjMifQ...") 
    private String token;

    @Schema(description = "토큰 만료 시간 (밀리초 단위)", 
            example = "3600000") 
    private Long expires;

    @Builder
    public TokenResponseDTO(String token, Long expires) {
        this.token = token;
        this.expires = expires;
    }
}