package com.ongi.backend.domain.centerstaff.service;

import com.ongi.backend.common.enums.Authority;
import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.security.JwtTokenizer;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.service.CenterService;
import com.ongi.backend.domain.centerstaff.dto.request.CenterStaffSignupRequest;
import com.ongi.backend.domain.centerstaff.dto.request.ValidateIdRequest;
import com.ongi.backend.domain.centerstaff.dto.response.CenterStaffSignupResponse;
import com.ongi.backend.domain.centerstaff.entity.CenterStaff;
import com.ongi.backend.domain.centerstaff.exception.CenterStaffErrorCase;
import com.ongi.backend.domain.centerstaff.repository.CenterStaffRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CenterStaffService {

    private final CenterStaffRepository centerStaffRepository;
    private final PasswordEncoder passwordEncoder;
    private final CenterService centerService;
    private final JwtTokenizer jwtTokenizer;

    public void validateId(@Valid ValidateIdRequest request) {
        if(existsDuplicateId(request.loginId())) {
            throw new ApplicationException(CenterStaffErrorCase.DUPLICATE_LOGIN_ID);
        }
    }

    @Transactional
    public CenterStaffSignupResponse signup(CenterStaffSignupRequest requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.password());

        if(requestDto.authority().equals(Authority.ROLE_SOCIAL_WORKER.toString())) {
            return socialWorkerSignup(requestDto, encodedPassword);
        } else if(requestDto.authority().equals(Authority.ROLE_CENTER_MANAGER.toString())) {
            return centerManagerSignup(requestDto, encodedPassword);
        } else {
            throw new ApplicationException(AuthErrorCase.INVALID_AUTHORITY);
        }
    }

    private boolean existsDuplicateId(String loginId) {
        return centerStaffRepository.existsByLoginId(loginId);
    }

    private CenterStaffSignupResponse socialWorkerSignup(CenterStaffSignupRequest requestDto, String encodedPassword) {
        if(requestDto.centerCode() == null)
            throw new ApplicationException(CenterStaffErrorCase.CENTER_INFO_REQUIRED);

        Center center = centerService.findCenterByCenterCode(requestDto.centerCode());
        CenterStaff centerStaff = saveCenterStaff(requestDto, encodedPassword, center);

        return new CenterStaffSignupResponse(centerStaff.getId(), null);
    }

    private CenterStaffSignupResponse centerManagerSignup(CenterStaffSignupRequest requestDto, String encodedPassword) {
        if(requestDto.centerId() == null)
            throw new ApplicationException(CenterStaffErrorCase.CENTER_INFO_REQUIRED);

        Center center = centerService.findCenterEntity(requestDto.centerId());
        CenterStaff centerStaff = saveCenterStaff(requestDto, encodedPassword, center);

        String userId = String.valueOf(centerStaff.getId());
        Map<String, Object> claims = Map.of("role", Authority.ROLE_CENTER_MANAGER, "centerId", requestDto.centerId());
        String accessToken = jwtTokenizer.createAccessToken(userId, claims);

        return new CenterStaffSignupResponse(centerStaff.getId(), accessToken);
    }

    private CenterStaff saveCenterStaff(CenterStaffSignupRequest requestDto, String encodedPassword, Center center) {
        return centerStaffRepository.save(CenterStaff.from(requestDto, encodedPassword, center));
    }

    public CenterStaff findByLoginId(String loginId) {
        return centerStaffRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(CenterStaffErrorCase.CENTER_STAFF_NOT_FOUND));
    }
}
