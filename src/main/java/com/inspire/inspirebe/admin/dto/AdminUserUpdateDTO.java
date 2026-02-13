package com.inspire.inspirebe.admin.dto;

import com.inspire.inspirebe.binding.Update;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "관리자 전용 사용자 정보 수정 요청 데이터 (시급 및 상태 수정 포함)")
public class AdminUserUpdateDTO extends UserUpdateDTO {

    @Schema(description = "변경할 시급", type = "integer", example = "10500")
    private Update<Integer> salary = Update.absent();

    @Schema(description = "변경할 사용자 상태", type = "string",
            example = "ACTIVE")
    private Update<String> status = Update.absent();
}