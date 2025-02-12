package com.ongi.backend.service;

import com.ongi.backend.dto.caregiver.CaregiverRequestDto;
import com.ongi.backend.entity.caregiver.Caregiver;
import com.ongi.backend.repository.caregiver.CaregiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;

    public void registerCaregiver(CaregiverRequestDto caregiverRequestDto) {

    }
}
