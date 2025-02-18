package com.ongi.backend.domain.matching.entity;


import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingCareDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // ✅ Matching과 N:1 관계
    @JoinColumn(name = "matching_id", nullable = false)
    private Matching matching;

    @Enumerated(EnumType.STRING)  // ✅ Enum을 문자열로 저장
    @Column(nullable = false, length = 50)
    private SeniorCareDetail careDetail;
}