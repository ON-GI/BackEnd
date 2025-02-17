package com.ongi.backend.domain.senior.dto.response;

import com.ongi.backend.common.enums.Gender;
import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.entity.enums.Residence;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorResponseDto {

    private String name;

    private LocalDate birthDate;

    private Integer age;

    private Gender gender;

    private String gradeType;

    private Residence residence;

    private String residenceType;

    private String profileImageUrl;

    private SeniorDiseaseResponseDto disease;

    private SeniorCareConditionResponseDto careCondition;

    public static SeniorResponseDto fromEntity(Senior senior) {
        return SeniorResponseDto.builder()
                .name(senior.getName())
                .birthDate(senior.getBirthDate())
                .age(senior.getAge())
                .gender(senior.getGender())
                .gradeType(senior.getGradeType().getDescription())
                .residence(senior.getResidence())
                .residenceType(senior.getResidenceType().getDescription())
                .profileImageUrl(senior.getProfileImageUrl())
                .disease(senior.getSeniorDisease() != null ? SeniorDiseaseResponseDto.from(senior.getSeniorDisease()) : null)
                .careCondition(senior.getCareCondition() != null ? SeniorCareConditionResponseDto.from(senior.getCareCondition()) : null)
                .build();
    }
}
