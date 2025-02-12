package com.ongi.backend.service;

import com.ongi.backend.entity.caregiver.Caregiver;
import com.ongi.backend.repository.caregiver.CaregiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;

    public void registerCaregiver(Caregiver caregiver) {

    }
}
