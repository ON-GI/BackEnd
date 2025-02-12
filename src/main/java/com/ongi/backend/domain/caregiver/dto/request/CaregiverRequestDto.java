package com.ongi.backend.domain.caregiver.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class CaregiverRequestDto {
    private String loginId;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
    private String description;
    private Boolean hasCar;
    private Boolean hasDementiaTraining;
    private String career;  // 문자열로 받음 (Enum 변환 예정)

    private WorkConditionRequestDto workCondition;

    private List<LicenseRequestDto> licenses;
}
