package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    Optional<Caregiver> findById(Long caregiverId);

    boolean existsByLoginId(String loginId);

    Optional<Caregiver> findByLoginId(String loginId);

    @Query("SELECT c FROM Caregiver c " +
            "LEFT JOIN FETCH c.caregiverInformation ci " +
            "LEFT JOIN FETCH ci.licenses " +
            "WHERE c.id = :caregiverId")
    Optional<Caregiver> findByIdJoinInfo(@Param("caregiverId") Long caregiverId);
}
