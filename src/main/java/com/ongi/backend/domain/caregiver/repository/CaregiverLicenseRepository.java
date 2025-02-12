package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.CaregiverLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverLicenseRepository extends JpaRepository<CaregiverLicense, Long> {
}
