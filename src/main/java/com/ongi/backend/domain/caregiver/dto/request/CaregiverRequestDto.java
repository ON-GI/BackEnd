package com.ongi.backend.domain.caregiver.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ongi.backend.domain.caregiver.entity.enums.DailyLivingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.FeedingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.MobilityAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.ToiletingAssistanceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;


@JsonIgnoreProperties({"toiletingAssistanceEnum", "feedingAssistanceEnum", "mobilityAssistanceEnum", "dailyLivingAssistanceEnum"})
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

        String description,

        @NotNull(message = "차 여부를 입력하세요.")
        Boolean hasCar,

        @NotNull(message = "치매 교육 이수 여부를 입력하세요.")
        Boolean hasDementiaTraining,

        @Valid
        @NotEmpty(message = "자격증을 입력하세요.")
        @Size(min = 1, message = "자격증을 최소 1개 이상 입력하세요.")
        List<LicenseRequestDto> licenses,

        String career,
        List<String> toiletingAssistance,
        List<String> feedingAssistance,
        List<String> mobilityAssistance,
        List<String> dailyLivingAssistance
) {

    public List<ToiletingAssistanceType> getToiletingAssistanceEnum() {
        return toiletingAssistance == null ? null
                : toiletingAssistance.stream()
                .map(ToiletingAssistanceType::fromString)
                .collect(Collectors.toList());
    }

    public List<FeedingAssistanceType> getFeedingAssistanceEnum() {
        return feedingAssistance == null ? null
                : feedingAssistance.stream()
                .map(FeedingAssistanceType::fromString)
                .collect(Collectors.toList());
    }

    public List<MobilityAssistanceType> getMobilityAssistanceEnum() {
        return mobilityAssistance == null ? null
                : mobilityAssistance.stream()
                .map(MobilityAssistanceType::fromString)
                .collect(Collectors.toList());
    }

    public List<DailyLivingAssistanceType> getDailyLivingAssistanceEnum() {
        return dailyLivingAssistance == null ? null
                : dailyLivingAssistance.stream()
                .map(DailyLivingAssistanceType::fromString)
                .collect(Collectors.toList());
    }
}
