package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.CaregiverInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverInformationRepository extends JpaRepository<CaregiverInformation, Long> {
}
