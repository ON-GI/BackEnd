package com.ongi.backend.domain.matching.dto.response;

import com.ongi.backend.domain.senior.entity.enums.Residence;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorMatchingDetailResponseDto {

    private String name;

    private LocalDate birthDate;

    private String gender;

    private Integer age;

    private Double weight;

    private String benefits;

    private Residence residence;

    private String gradeType;

    private String residenceType;

    private String profileImageUrl;

    private String staffContact;

    private List<MatchingCareTimeResponseDto> careTimes;

    private List<String> careDetails;

    private List<String> dementiaSymptoms;

    @QueryProjection  // QueryDSL이 사용할 수 있도록 생성자 지정
    public SeniorMatchingDetailResponseDto(String name, LocalDate birthDate, String gender, Integer age,
                                           Double weight, String benefits, Residence residence, String gradeType,
                                           String residenceType, String profileImageUrl, String staffContact) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.benefits = benefits;
        this.residence = residence;
        this.gradeType = gradeType;
        this.residenceType = residenceType;
        this.profileImageUrl = profileImageUrl;
        this.staffContact = staffContact;
    }

    public void updateCareTimes(List<MatchingCareTimeResponseDto> careTimes) {
        this.careTimes = careTimes;
    }

    public void updateCareDetails(List<String> careDetails) {
        this.careDetails = careDetails;
    }

    public void updateDementiaSymptoms(List<String> dementiaSymptoms) {
        this.dementiaSymptoms = dementiaSymptoms;
    }
}
