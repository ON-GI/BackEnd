package com.ongi.backend.domain.senior.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE senior_disease SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
public class SeniorDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private Senior senior;

    @Column
    private String disease;

    @Column
    private String additionalDementiaSymptoms; // 사용자가 직접 입력한 기타 치매 증상


}
