package com.ongi.backend.domain.center.dto.response;

import com.ongi.backend.domain.center.entity.Center;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CenterEssentialResponseDto(
        String centerName,
        String contact,
        String address,
        String profileImageUrl
) {

    public static CenterEssentialResponseDto from(Center center) {
        return CenterEssentialResponseDto.builder()
                .centerName(center.getName())
                .contact(center.getContact())
                .address(center.getAddress())
                .profileImageUrl(center.getProfileImageUrl())
                .build();
    }
}
