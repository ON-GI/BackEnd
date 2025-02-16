package com.ongi.backend.domain.senior.service;


import com.ongi.backend.domain.senior.dto.request.SeniorCareConditionRequestDto;
import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.entity.SeniorCareCondition;
import com.ongi.backend.domain.senior.entity.SeniorCareTime;
import com.ongi.backend.domain.senior.entity.SeniorCareTypeMapping;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import com.ongi.backend.domain.senior.repository.SeniorCareConditionRepository;
import com.ongi.backend.domain.senior.repository.SeniorCareTimeRepository;
import com.ongi.backend.domain.senior.repository.SeniorCareTypeMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeniorCareConditionService {

    private final SeniorCareConditionRepository seniorCareConditionRepository;
    private final SeniorCareTypeMappingRepository seniorCareTypeMappingRepository;
    private final SeniorCareTimeRepository seniorCareTimeRepository;

    @Transactional
    public SeniorCareCondition saveCareCondition(SeniorCareConditionRequestDto request, Senior senior) {
        SeniorCareCondition careCondition = SeniorCareCondition.from(request, senior);
        seniorCareConditionRepository.save(careCondition);

        // 필요한 서비스 타입 매핑 저장
        for (String detail : request.careDetails()) {
            SeniorCareTypeMapping careTypeMapping = SeniorCareTypeMapping.builder()
                    .seniorCareCondition(careCondition)
                    .seniorCareDetail(SeniorCareDetail.valueOf(detail))
                    .build();
            seniorCareTypeMappingRepository.save(careTypeMapping);
        }

        // 캐어 시간 저장
        List<SeniorCareTime> careTimes = request.careTimes().stream()
                .map(time -> SeniorCareTime.from(time, careCondition))
                .toList();
        seniorCareTimeRepository.saveAll(careTimes);

        return careCondition;
    }
}
