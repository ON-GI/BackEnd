package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "senior_care_type_mapping")
@SQLDelete(sql = "UPDATE senior_care_type_mapping SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class SeniorCareTypeMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_care_condition_id", nullable = false)
    private SeniorCareCondition seniorCareCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeniorCareDetail seniorCareDetail;
}
