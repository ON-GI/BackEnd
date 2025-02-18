package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

}
