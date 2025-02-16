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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        return generateTokens(caregiver.getId(), Authority.ROLE_CAREGIVER);
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

        return generateTokens(userId, authority);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(AuthErrorCase.WRONG_PASSWORD);
        }
    }

    private LoginTokensDto generateTokens(Long userId, Authority authority) {
        String accessToken = jwtTokenizer.createAccessToken(String.valueOf(userId), Map.of("role", authority));
        String refreshToken = jwtTokenizer.createRefreshToken(String.valueOf(userId), Map.of("role", authority));

        saveRefreshToken(userId, authority, refreshToken);
        return new LoginTokensDto(accessToken, refreshToken);
    }

    private void saveRefreshToken(Long userId, Authority authority, String refreshToken) {
        refreshTokenRepository.deleteByUserIdAndAuthority(userId, authority);
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
