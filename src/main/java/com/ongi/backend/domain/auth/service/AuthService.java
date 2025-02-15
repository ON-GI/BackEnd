package com.ongi.backend.domain.auth.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.security.JwtTokenizer;
import com.ongi.backend.domain.auth.dto.LoginTokensDto;
import com.ongi.backend.domain.auth.dto.request.CaregiverLoginRequest;
import com.ongi.backend.domain.auth.entity.RefreshToken;
import com.ongi.backend.domain.auth.entity.enums.Authority;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.auth.repository.RefreshTokenRepository;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.service.CaregiverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CaregiverService caregiverService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Transactional
    public LoginTokensDto caregiverLogin(CaregiverLoginRequest request) {
        Caregiver caregiver = caregiverService.findByLoginId(request.loginId());
        validatePassword(request.password(), caregiver.getPassword());

        String subject = String.valueOf(caregiver.getId());
        String accessToken = jwtTokenizer.createAccessToken(subject, Map.of("role", "CARE_GIVER"));
        String refreshToken = jwtTokenizer.createRefreshToken(subject, Map.of("role", "CARE_GIVER"));

        saveRefreshToken(caregiver.getId(), Authority.ROLE_CAREGIVER, refreshToken);

        return new LoginTokensDto(accessToken, refreshToken);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(AuthErrorCase.WRONG_PASSWORD);
        }
    }

    private void saveRefreshToken(Long userId, Authority authority, String refreshToken) {
        refreshTokenRepository.deleteByUserIdAndAuthority(userId, authority);
        refreshTokenRepository.save(RefreshToken.from(userId, authority, refreshToken));
    }

}
