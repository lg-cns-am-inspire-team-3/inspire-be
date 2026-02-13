package com.inspire.inspirebe.user.dto;

import com.inspire.inspirebe.binding.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "사용자 정보 수정 요청 데이터") //
public class UserUpdateDTO {

    @Schema(description = "이름", implementation = String.class, // [핵심] Update 객체가 아닌 String으로 인식하게 함
            example = "tester1")
    private Update<String> name = Update.absent();

    @Schema(description = "연락처", implementation = String.class, example = "010-1234-5678")
    private Update<String> contact = Update.absent();

    @Schema(description = "이메일", implementation = String.class, example = "qqqqtest1@example.com")
    private Update<String> email = Update.absent();

    @Schema(description = "거주지 주소", implementation = String.class, example = "서울시 동대문구")
    private Update<String> address = Update.absent();
}