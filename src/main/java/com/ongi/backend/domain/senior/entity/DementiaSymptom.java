package com.ongi.backend.domain.senior.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DementiaSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symptomName;  // 예: "집 밖을 재회", "가족을 알아보지 못함"

    public DementiaSymptom(String symptomName) {
        this.symptomName = symptomName;
    }
}