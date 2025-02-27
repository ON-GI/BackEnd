package com.ongi.backend.domain.caregiver.service;

import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkTimeRequestDto;
import com.ongi.backend.domain.caregiver.dto.response.WorkConditionResponseDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkRegionRequestDto;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkRegion;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkTime;
import com.ongi.backend.domain.caregiver.exception.CaregiverNotFoundException;
import com.ongi.backend.domain.caregiver.exception.WorkConditionNotFoundException;
import com.ongi.backend.domain.caregiver.repository.CaregiverRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverWorkConditionRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverWorkRegionRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaregiverWorkService {

    private final CaregiverRepository caregiverRepository;

    private final CaregiverWorkConditionRepository caregiverWorkConditionRepository;

    private final CaregiverWorkRegionRepository caregiverWorkRegionRepository;

    private final CaregiverWorkTimeRepository caregiverWorkTimeRepository;

    @Transactional(readOnly = true)
    public WorkConditionResponseDto getWorkConditionByCaregiverId(Long caregiverId) {
        CaregiverWorkCondition workCondition = caregiverWorkConditionRepository.findByCaregiverId(caregiverId)
                .orElseThrow(WorkConditionNotFoundException::new);

        return WorkConditionResponseDto.fromEntity(workCondition);
    }

    private void registerWorkCondition(WorkConditionRequestDto workConditionRequestDto, Long caregiverId) {
        if (workConditionRequestDto == null) return;

        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(CaregiverNotFoundException::new);

        // CaregiverWorkCondition 저장
        CaregiverWorkCondition workCondition = CaregiverWorkCondition.from(workConditionRequestDto, caregiver);
        caregiverWorkConditionRepository.save(workCondition);

        // WorkRegion 저장
        saveWorkRegions(workConditionRequestDto.getWorkRegions(), workCondition);

        // WorkTime 저장
        saveWorkTimes(workConditionRequestDto.getWorkTimes(), workCondition);
    }

    @Transactional
    public void updateWorkCondition(WorkConditionRequestDto workConditionRequestDto, Long caregiverId) {

        Optional<CaregiverWorkCondition> optionalWorkCondition = caregiverWorkConditionRepository.findByCaregiverId(caregiverId);

        if (optionalWorkCondition.isPresent()) {
            CaregiverWorkCondition workCondition = optionalWorkCondition.get();
            workCondition.updatePay(workConditionRequestDto.getPayType(), workConditionRequestDto.getPayAmount());

            caregiverWorkConditionRepository.save(workCondition);

            // 기존 WorkRegion 및 WorkTime 삭제 후 새로 저장
            caregiverWorkRegionRepository.deleteByWorkCondition(workCondition);
            caregiverWorkTimeRepository.deleteByWorkCondition(workCondition);

            saveWorkRegions(workConditionRequestDto.getWorkRegions(), workCondition);
            saveWorkTimes(workConditionRequestDto.getWorkTimes(), workCondition);
        } else{
            registerWorkCondition(workConditionRequestDto, caregiverId);
        }
    }

    /**
     * 근무 가능 지역(WorkRegion) 저장
     */
    private void saveWorkRegions(List<WorkRegionRequestDto> workRegionRequestDto, CaregiverWorkCondition workCondition) {
        if (workRegionRequestDto == null || workRegionRequestDto.isEmpty()) return;

        List<CaregiverWorkRegion> regions = workRegionRequestDto.stream()
                .map(dto -> CaregiverWorkRegion.from(dto, workCondition))
                .collect(Collectors.toList());

        caregiverWorkRegionRepository.saveAll(regions);
    }

    /**
     * 근무 가능 시간(WorkTime) 저장
     */
    private void saveWorkTimes(List<WorkTimeRequestDto> workTimeRequestDtos, CaregiverWorkCondition workCondition) {
        if (workTimeRequestDtos == null || workTimeRequestDtos.isEmpty()) return;

        List<CaregiverWorkTime> times = workTimeRequestDtos.stream()
                .map(dto -> CaregiverWorkTime.from(dto, workCondition))
                .collect(Collectors.toList());

        caregiverWorkTimeRepository.saveAll(times);
    }

    public void deleteWorkCondition(Long caregiverId) {
        CaregiverWorkCondition workCondition = caregiverWorkConditionRepository.findByCaregiverId(caregiverId)
                .orElseThrow(WorkConditionNotFoundException::new);

        caregiverWorkRegionRepository.deleteByWorkCondition(workCondition);
        caregiverWorkTimeRepository.deleteByWorkCondition(workCondition);

        caregiverWorkConditionRepository.delete(workCondition);
    }
}
