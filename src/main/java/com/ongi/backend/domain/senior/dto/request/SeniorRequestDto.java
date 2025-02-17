package com.ongi.backend.domain.senior.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ongi.backend.common.entity.Gender;
import com.ongi.backend.domain.senior.entity.enums.GradeType;
import com.ongi.backend.domain.senior.entity.enums.Residence;
import com.ongi.backend.domain.senior.entity.enums.ResidenceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonIgnoreProperties({"careDetailsEnum"})
public record SeniorRequestDto (

        @NotBlank(message = "이름을 입력하세요.")
        String name,

        @NotNull(message = "생년월일을 입력하세요.")
        LocalDate birthDate,

        @NotNull(message = "나이를 입력하세요.")
        Integer age,

        @NotNull(message = "성별을 입력하세요.")
        Gender gender,

        @NotNull(message = "요양등급을 입력하세요.")
        GradeType gradeType,

        @Valid
        @NotNull(message = "거주지를 입력하세요.")
        ResidenceDto residence,

        @NotNull(message = "거주 형태를 입력하세요.")
        ResidenceType residenceType,

        String profileImageUrl,

        @Valid
        SeniorCareConditionRequestDto careCondition,  // 어르신의 필요 서비스 항목

        @Valid
        SeniorDiseaseRequestDto diseaseCondition // 어르신의 질병 관련 정보
) {
    public record ResidenceDto(
            @NotBlank(message = "시/도를 입력하세요.") String city,
            @NotBlank(message = "구/군을 입력하세요.") String district,
            @NotBlank(message = "동/읍/면을 입력하세요.") String town
    ) {
        public Residence toEntity() {
            return new Residence(city, district, town);
        }
    }
}
