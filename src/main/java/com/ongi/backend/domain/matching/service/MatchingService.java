package com.ongi.backend.domain.matching.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.service.CaregiverService;
import com.ongi.backend.domain.matching.dto.request.MatchingRequestDto;
import com.ongi.backend.domain.matching.entity.Matching;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.matching.exception.MatchingErrorCase;
import com.ongi.backend.domain.matching.repository.MatchingRepository;
import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.service.SeniorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {

    private final MatchingRepository matchingRepository;

    private final SeniorService seniorService;

    private final CaregiverService caregiverService;

    public Matching findMatchingEntity(Long matchingId) {
        return matchingRepository.findById(matchingId)
                .orElseThrow(() -> new ApplicationException(MatchingErrorCase.MATCHING_NOT_FOUND));
    }

    public void registerMatching(MatchingRequestDto matchingRequestDto, Long centerId) {

        Senior senior = seniorService.findSeniorEntity(matchingRequestDto.seniorId(), centerId);
        Matching matching = Matching.from(matchingRequestDto, senior);

        matchingRepository.save(matching);
    }

    public void findMatchingDetail(Long matchingId) {

    }

    public void findMatchingByCenter(Long centerId) {

    }

    public void findMatchingByCaregiver(Long caregiverId) {

    }

    public void findOptimalCaregivers(Long matchingId) {

    }

    public void requestMatching(Long matchingId, Long caregiverId) {
        Caregiver caregiver = caregiverService.findCaregiverById(caregiverId);

        Matching matching = findMatchingEntity(matchingId);
        matching.updateCaregiver(caregiver);
        matching.updateMatchingStatus(MatchingStatus.PENDING_UNVIEW);
    }

    public void cancelMatching(Long matchingId) {

    }

    public void adjustingMatching(Long matchingId) {

    }

    public void completeMatching(Long matchingId) {

    }
}
