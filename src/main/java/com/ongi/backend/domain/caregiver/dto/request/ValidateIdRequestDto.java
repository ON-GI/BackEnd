package com.ongi.backend.domain.caregiver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ValidateIdRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;
}
