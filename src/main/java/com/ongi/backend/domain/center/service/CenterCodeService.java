package com.ongi.backend.domain.center.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CenterCodeService {

    @Value("${app.center.code.characters}")  // 프로퍼티에서 문자 목록 가져오기
    private String codeCharacters;

    @Value("${app.center.code.length}")  // 코드 길이 가져오기
    private Integer codeLength;

    private final Random random = new SecureRandom(); // SecureRandom 사용

    /**
     * ✅ 랜덤 센터 코드 생성
     */
    public String generateCenterCode() {
        StringBuilder code = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(codeCharacters.length());
            code.append(codeCharacters.charAt(index));
        }
        return code.toString();
    }
}
