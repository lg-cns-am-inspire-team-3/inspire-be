package com.inspire.inspirebe.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TokenResponseDTO {
    // access token
    private String token;
    // 만료 기한
    private Long expires;

    @Builder
    public TokenResponseDTO(String token, Long expires) {
        this.token = token;
        this.expires = expires;
    }
}
