package com.ongi.backend.domain.center.dto.response;

import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.entity.CenterGrade;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CenterResponseDto {

    private String centerName;

    private Integer establishmentYear;

    private Integer operatingPeriod;

    private String email;

    private String contact;

    private String address;

    private CenterGrade centerGrade;

    private String description;

    private Boolean hasVehicle;

    private String centerCode;

    public static CenterResponseDto fromEntity(Center center) {
        return CenterResponseDto.builder()
                .centerName(center.getName())
                .establishmentYear(center.getEstablishmentDate().getYear())
                .operatingPeriod(calculateEstablishedYears(center.getEstablishmentDate()))
                .email(center.getEmail())
                .contact(center.getContact())
                .address(center.getAddress())
                .centerGrade(center.getCenterGrade())
                .description(center.getDescription())
                .hasVehicle(center.getHasVehicle())
                .centerCode(center.getCenterCode())
                .build();
    }

    private static Integer calculateEstablishedYears(LocalDate establishedDate) {
        if (establishedDate == null) {
            return 0; // 설립일이 없으면 0 반환
        }
        return LocalDate.now().getYear() - establishedDate.getYear() + 1;
    }

}
