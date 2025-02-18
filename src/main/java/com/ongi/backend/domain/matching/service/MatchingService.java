package com.ongi.backend.domain.matching.service;

import com.ongi.backend.domain.caregiver.service.CaregiverService;
import com.ongi.backend.domain.matching.dto.request.MatchingRequestDto;
import com.ongi.backend.domain.matching.repository.MatchingRepository;
import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.service.SeniorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {

    private final MatchingRepository matchingRepository;

    private final SeniorService seniorService;

    private final CaregiverService caregiverService;

    public void registerMatching(MatchingRequestDto matchingRequestDto, Long centerId) {

        Senior senior = seniorService.findSeniorEntity(matchingRequestDto.seniorId(), centerId);
    }
}
