package com.ongi.backend.domain.auth.service;

import com.ongi.backend.common.enums.Authority;
import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.security.JwtTokenizer;
import com.ongi.backend.domain.auth.dto.LoginTokensDto;
import com.ongi.backend.domain.auth.dto.request.LoginRequest;
import com.ongi.backend.domain.auth.entity.RefreshToken;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.auth.repository.RefreshTokenRepository;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.service.CaregiverService;
import com.ongi.backend.domain.centerstaff.entity.CenterStaff;
import com.ongi.backend.domain.centerstaff.service.CenterStaffService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CaregiverService caregiverService;
    private final CenterStaffService centerStaffService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Transactional
    public LoginTokensDto login(LoginRequest request) {
        if(request.authority().equals(Authority.ROLE_CAREGIVER.toString())) {
            return caregiverLogin(request);
        } else if(request.authority().equals(Authority.ROLE_CENTER_MANAGER.toString()) || request.authority().equals(Authority.ROLE_SOCIAL_WORKER.toString())) {
            return centerStaffLogin(request);
        } else {
            throw new ApplicationException(AuthErrorCase.INVALID_AUTHORITY);
        }
    }

    @Transactional
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Authority authority = Authority.valueOf(authentication.getAuthorities().iterator().next().getAuthority());

            refreshTokenRepository.deleteByUserIdAndAuthority(userId, authority);
        }
    }

    @Transactional
    public LoginTokensDto refreshAccessToken(String refreshToken) {
        jwtTokenizer.validateRefreshToken(refreshToken);

        Long userId = jwtTokenizer.getUserIdFromRefreshToken(refreshToken);
        Authority authority = jwtTokenizer.getAuthorityFromRefreshToken(refreshToken);

        RefreshToken storedToken = findRefreshToken(userId, authority);
        validateRefreshTokenMatch(storedToken, refreshToken);

        if(authority.equals(Authority.ROLE_CAREGIVER)) {
            return generateTokens(userId, authority, Map.of("role", authority));
        } else {
            return generateTokens(
                    userId,
                    authority,
                    Map.of("role", authority, "centerId", jwtTokenizer.getCenterIdFromRefreshToken(refreshToken))
            );
        }
    }

    public LoginTokensDto caregiverLogin(LoginRequest request) {
        Caregiver caregiver = caregiverService.findByLoginId(request.loginId());
        validatePassword(request.password(), caregiver.getPassword());

        return generateTokens(
                caregiver.getId(),
                Authority.ROLE_CAREGIVER,
                Map.of("role", Authority.ROLE_CAREGIVER)
        );
    }

    private LoginTokensDto centerStaffLogin(LoginRequest request) {
        CenterStaff centerStaff = centerStaffService.findByLoginId(request.loginId());
        validatePassword(request.password(), centerStaff.getPassword());

        if(!centerStaff.getApproval()) {
            throw new ApplicationException(AuthErrorCase.NOT_APPROVAL);
        }

        Authority authority = centerStaff.getAuthority();
        return generateTokens(
                centerStaff.getId(),
                authority,
                Map.of("role", authority, "centerId", centerStaff.getCenter().getId())
        );
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(AuthErrorCase.WRONG_PASSWORD);
        }
    }

    private LoginTokensDto generateTokens(Long userId, Authority authority, Map<String, Object> claims) {
        String accessToken = jwtTokenizer.createAccessToken(String.valueOf(userId), claims);
        String refreshToken = jwtTokenizer.createRefreshToken(String.valueOf(userId), claims);

        saveRefreshToken(userId, authority, refreshToken);
        return new LoginTokensDto(accessToken, refreshToken);
    }

    private void saveRefreshToken(Long userId, Authority authority, String refreshToken) {
        refreshTokenRepository.deleteByUserIdAndAuthority(userId, authority);
        refreshTokenRepository.flush();
        refreshTokenRepository.save(RefreshToken.from(userId, authority, refreshToken));
    }

    private void validateRefreshTokenMatch(RefreshToken storedToken, String refreshToken) {
        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            refreshTokenRepository.deleteByUserIdAndAuthority(storedToken.getUserId(), storedToken.getAuthority());
            throw new ApplicationException(AuthErrorCase.REFRESH_TOKEN_NOT_EQUAL);
        }
    }

    private RefreshToken findRefreshToken(Long userId, Authority authority) {
        return refreshTokenRepository.findByUserIdAndAuthority(userId, authority)
                .orElseThrow(() -> new ApplicationException(AuthErrorCase.REFRESH_TOKEN_NOT_FOUND));
    }
}
