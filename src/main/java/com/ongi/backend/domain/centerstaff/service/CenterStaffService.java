package com.ongi.backend.domain.centerstaff.service;

import com.ongi.backend.common.enums.Authority;
import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.service.CenterService;
import com.ongi.backend.domain.centerstaff.dto.request.CenterStaffSignupRequest;
import com.ongi.backend.domain.centerstaff.dto.response.CenterStaffSignupResponse;
import com.ongi.backend.domain.centerstaff.entity.CenterStaff;
import com.ongi.backend.domain.centerstaff.exception.CenterStaffErrorCase;
import com.ongi.backend.domain.centerstaff.repository.CenterStaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterStaffService {

    private final CenterStaffRepository centerStaffRepository;
    private final PasswordEncoder passwordEncoder;
    private final CenterService centerService;

    @Transactional
    public CenterStaffSignupResponse signup(CenterStaffSignupRequest requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.password());

        CenterStaff centerStaff;
        if(requestDto.authority().equals(Authority.ROLE_SOCIAL_WORKER.toString())) {
            centerStaff = socialWorkerSignup(requestDto, encodedPassword);
        } else if(requestDto.authority().equals(Authority.ROLE_CENTER_MANAGER.toString())) {
            centerStaff = centerManagerSignup(requestDto, encodedPassword);
        } else {
            throw new ApplicationException(AuthErrorCase.INVALID_AUTHORITY);
        }

        return new CenterStaffSignupResponse(centerStaff.getId());
    }

    private CenterStaff socialWorkerSignup(CenterStaffSignupRequest requestDto, String encodedPassword) {
        if(requestDto.centerCode() == null)
            throw new ApplicationException(CenterStaffErrorCase.CENTER_INFO_REQUIRED);

        Center center = centerService.findCenterByCenterCode(requestDto.centerCode());
        return saveCenterStaff(requestDto, encodedPassword, center);

    }

    private CenterStaff centerManagerSignup(CenterStaffSignupRequest requestDto, String encodedPassword) {
        if(requestDto.centerId() == null)
            throw new ApplicationException(CenterStaffErrorCase.CENTER_INFO_REQUIRED);

        Center center = centerService.findCenterEntity(requestDto.centerId());
        return saveCenterStaff(requestDto, encodedPassword, center);
    }

    private CenterStaff saveCenterStaff(CenterStaffSignupRequest requestDto, String encodedPassword, Center center) {
        return centerStaffRepository.save(CenterStaff.from(requestDto, encodedPassword, center));
    }
}
