package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.InformationRequestDto;
import com.ongi.backend.domain.caregiver.entity.enums.DailyLivingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.FeedingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.MobilityAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.ToiletingAssistanceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CaregiverInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean hasDementiaTraining;    // 치매 교육 이수 여부

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<ToiletingAssistanceType> toiletingAssistance;  // 베변보조 경험

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<FeedingAssistanceType> feedingAssistance;  // 식사보조 경험

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<MobilityAssistanceType> mobilityAssistance;    // 이동보조 경험

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<DailyLivingAssistanceType> dailyLivingAssistance;  // 일상생활보조 경험

    @OneToMany(mappedBy = "caregiverInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverLicense> licenses;    // 자격증

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;

    public static CaregiverInformation from(InformationRequestDto request, Caregiver caregiver) {
        return CaregiverInformation.builder()
                .hasDementiaTraining(request.hasDementiaTraining())
                .toiletingAssistance(request.getToiletingAssistanceEnum())
                .feedingAssistance(request.getFeedingAssistanceEnum())
                .mobilityAssistance(request.getMobilityAssistanceEnum())
                .dailyLivingAssistance(request.getDailyLivingAssistanceEnum())
                .caregiver(caregiver)
                .licenses(new ArrayList<CaregiverLicense>())
                .build();
    }

    public void updateLicenses(List<CaregiverLicense> licenses) {
        this.licenses.clear();
        this.licenses.addAll(licenses);
    }

    public void update(InformationRequestDto request) {
        this.hasDementiaTraining = request.hasDementiaTraining();
        this.toiletingAssistance = request.getToiletingAssistanceEnum();
        this.feedingAssistance = request.getFeedingAssistanceEnum();
        this.mobilityAssistance = request.getMobilityAssistanceEnum();
        this.dailyLivingAssistance = request.getDailyLivingAssistanceEnum();
    }
}
