package com.ongi.backend.domain.auth.controller;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.auth.dto.LoginTokensDto;
import com.ongi.backend.domain.auth.dto.request.CaregiverLoginRequest;
import com.ongi.backend.domain.auth.dto.response.CaregiverLoginResponse;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.ongi.backend.common.security.JwtTokenizer.REFRESH_TOKEN_EXPIRATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/caregiver/login")
    public CommonResponse<CaregiverLoginResponse> login(@Valid @RequestBody CaregiverLoginRequest requestDto,
                                                        HttpServletResponse httpServletResponse) {
        LoginTokensDto tokens = authService.caregiverLogin(requestDto);
        setRefreshTokenCookie(httpServletResponse, tokens.refreshToken());
        return CommonResponse.success(new CaregiverLoginResponse(tokens.accessToken()));
    }

    @DeleteMapping("/logout")
    public CommonResponse<Object> logout(HttpServletResponse httpServletRespons) {
        authService.logout();
        deleteRefreshTokenCookie(httpServletRespons);
        return CommonResponse.success();
    }

    @PostMapping("/refresh")
    public CommonResponse<CaregiverLoginResponse> refreshAccessToken(HttpServletRequest httpServletRequest,
                                                                     HttpServletResponse httpServletResponse) {
        String refreshToken = extractRefreshToken(httpServletRequest);

        try {
            LoginTokensDto tokens = authService.refreshAccessToken(refreshToken);
            setRefreshTokenCookie(httpServletResponse, tokens.refreshToken());
            return CommonResponse.success(new CaregiverLoginResponse(tokens.accessToken()));
        } catch (ApplicationException e) {
            deleteRefreshTokenCookie(httpServletResponse);
            throw e;
        }
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRATION / 1000));
        response.addCookie(cookie);
    }

    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 쿠키 만료
        response.addCookie(refreshTokenCookie);
    }

    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
