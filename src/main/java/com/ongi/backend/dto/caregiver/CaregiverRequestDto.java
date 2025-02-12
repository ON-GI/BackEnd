package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.Caregiver;
import com.ongi.backend.entity.caregiver.CaregiverCareer;
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
    private boolean hasCar;
    private boolean hasDementiaTraining;
    private String career;  // 문자열로 받음 (Enum 변환 예정)

    private CaregiverWorkConditionDto workCondition;

    private List<CaregiverLicenseDto> licenses;

    public Caregiver toEntity() {
        return Caregiver.builder()
                .loginId(this.loginId)
                .password(this.password) // 암호화된 비밀번호 적용
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .description(this.description)
                .hasCar(this.hasCar)
                .hasDementiaTraining(this.hasDementiaTraining)
                .career(CaregiverCareer.valueOf(this.career))
                .build();
    }
}
