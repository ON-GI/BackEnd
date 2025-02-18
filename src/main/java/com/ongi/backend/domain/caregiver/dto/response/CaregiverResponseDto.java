package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.Caregiver;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CaregiverResponseDto(
        String name,
        String phoneNum,
        String address,
        String profileImageUrl,
        Boolean hasCar
) {
    public static CaregiverResponseDto from(Caregiver caregiver) {
        return CaregiverResponseDto.builder()
                .name(caregiver.getName())
                .phoneNum(caregiver.getPhoneNumber())
                .address(caregiver.getAddress())
                .profileImageUrl(caregiver.getProfileImageUrl())
                .hasCar(caregiver.isHasCar())
                .build();
    }
}
