package com.ongi.backend.domain.centerstaff.dto.response;

import com.ongi.backend.domain.centerstaff.entity.CenterStaff;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CenterStaffResponse(
        String loginId,
        String authority,
        Long centerId
) {

    public static CenterStaffResponse from(CenterStaff centerStaff) {
        return CenterStaffResponse.builder()
                .loginId(centerStaff.getLoginId())
                .authority(centerStaff.getAuthority().name())
                .centerId(centerStaff.getId())
                .build();
    }

}
