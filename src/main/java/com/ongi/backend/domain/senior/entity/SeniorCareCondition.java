package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE senior_care_condition SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
public class SeniorCareCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private Senior senior;

    // 어르신이 필요한 서비스 항목들 (N:M 관계)
    @OneToMany(mappedBy = "seniorCareCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeniorCareTypeMapping> careTypes = new ArrayList<>();

    @OneToMany(mappedBy = "careCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeniorCareTime> seniorCareTimes;   // 캐어 필요 시간
}
