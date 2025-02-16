package com.ongi.backend.domain.caregiver.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CaregiverRequestDto (

        @NotBlank(message = "아이디를 입력하세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력하세요.")
        String password,

        @NotBlank(message = "이름을 입력하세요.")
        String name,

        @NotBlank(message = "전화번호를 입력하세요.")
        String phoneNumber,

        @NotBlank(message = "주소를 입력하세요.")
        String address,

        @NotNull(message = "차 여부를 입력하세요.")
        Boolean hasCar,

        @Valid
        @NotNull(message = "근무 정보를 입력하세요")
        InformationRequestDto information,

        OptionalRequestDto optional
) {
}
