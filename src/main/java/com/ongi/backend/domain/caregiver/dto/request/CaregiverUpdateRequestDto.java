package com.ongi.backend.domain.caregiver.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CaregiverUpdateRequestDto(
        @NotBlank(message = "이름을 입력하세요.")
        String name,

        @NotBlank(message = "전화번호를 입력하세요.")
        String phoneNum,

        @NotBlank(message = "주소를 입력하세요.")
        String address,

        @NotNull(message = "차량여부를 입력하세요.")
        Boolean hasCar
) {
}
