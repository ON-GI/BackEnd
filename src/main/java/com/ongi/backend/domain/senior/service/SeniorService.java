package com.ongi.backend.domain.senior.service;

import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.entity.*;
import com.ongi.backend.domain.senior.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SeniorService {

    private final SeniorRepository seniorRepository;
    private final SeniorCareConditionService seniorCareConditionService;
    private final SeniorDiseaseService seniorDiseaseService;

    @Transactional
    public Senior registerSenior(SeniorRequestDto request, Center center) {

        // 어르신 엔티티 생성 후 저장
        Senior senior = Senior.from(request, center);
        seniorRepository.save(senior);

        // 캐어 조건 저장 (SeniorCareConditionService 호출)
        seniorCareConditionService.saveCareCondition(request.careCondition(), senior);

        // 질병 정보 저장 (SeniorDiseaseService 호출)
        seniorDiseaseService.saveDisease(request.diseaseCondition(), senior);

        return senior;
    }
}
