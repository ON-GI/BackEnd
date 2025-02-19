package com.ongi.backend.domain.center.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CenterEssentialRequestDto(
        @NotBlank(message = "센터명을 입력하세요.")
        String centerName,

        @NotBlank(message = "전화번호를 입력하세요.")
        String contact,

        @NotBlank(message = "주소를 입력하세요.")
        String address
) {
}
