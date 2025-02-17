package com.ongi.backend.domain.center.dto.request;

import com.ongi.backend.domain.center.entity.enums.CenterGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CenterRequestDto (

    @NotBlank(message = "이름을 입력하세요.")
    String centerName,

    @NotNull(message = "센터 ID를 입력해주세요.")
    Long centerId,

    @NotNull(message = "전화번호를 입력하세요.")
    String contact,

    @NotNull(message = "이메일 주소를 입력하세요.")
    String email,

    @NotNull(message = "센터 주소를 입력하세요.")
    String address,

    @NotNull(message = "센터 등급을 입력하세요.")
    CenterGrade centerGrade,

    @NotNull(message = "센터 운영 기간을 입력하세요.")
    String operatingPeriod,

    String description,

    @NotNull(message = "차량 보유 여부를 선택해주세요.")
    Boolean hasVehicle
) {

}
