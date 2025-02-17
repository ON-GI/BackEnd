package com.ongi.backend.domain.center.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.center.dto.response.CenterResponseDto;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.exception.CenterErrorCase;
import com.ongi.backend.domain.center.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CenterService {

    private final CenterRepository centerRepository;

    @Transactional(readOnly = true)
    public Center findCenterEntity(Long centerId) {
        return centerRepository.findById(centerId)
                .orElseThrow(() -> new ApplicationException(CenterErrorCase.CENTER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<CenterResponseDto> findCenterByName(String centerName) {
        List<Center> centers = centerRepository.findTop10ByNameStartingWith(centerName);

        return centers.stream()
                .map(CenterResponseDto::fromEntity)  // ✅ 변환 적용
                .toList();
    }
}
