package com.inspire.inspirebe.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "사용자 정보 조회 응답 DTO")
public class UserResponseDTO {
    @Schema(
            description = "사용자 고유 ID",
            example = "3"
    )
    private Long id;

    @Schema(
            description = "로그인 아이디",
            example = "seungjun123"
    )
    private String loginId;

    @Schema(
            description = "이메일 주소",
            example = "test@email.com",
            format = "email"
    )
    private String email;

    @Schema(
            description = "사용자 이름",
            example = "홍길동"
    )
    private String name;

    @Schema(
            description = "연락처",
            example = "010-1234-5678"
    )
    private String contact;

    @Schema(
            description = "주소 (미입력 시 null 가능)",
            example = "서울특별시 강남구",
            nullable = true
    )
    private String address;

    @Schema(
            description = "시급",
            example = "10000",
            nullable = true
    )
    private Integer salary;

    @Builder
    public UserResponseDTO(Long id, String loginId, String email, String name, String contact, String address, Integer salary) {
        this.id = id;
        this.loginId = loginId;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.salary = salary;
    }
}
