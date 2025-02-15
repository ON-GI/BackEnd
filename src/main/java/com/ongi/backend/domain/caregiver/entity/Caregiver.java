package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.CaregiverRequestDto;
import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.caregiver.entity.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE caregiver SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
@Table(name = "caregiver")
public class Caregiver extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId; // 로그인 ID (회원가입 시 입력)

    @Column(nullable = false)
    private String password;    // 비밀번호 (암호화 필요)

    @Column(nullable = false)
    private String name;    // 이름

    @Column(nullable = false)
    private String phoneNumber; // 연락처

    @Column(nullable = false)
    private String address;     // 주소

    private String profileImageUrl; // 프로필 사진 경로

    private String description;     // 한 줄 소개

    @Column(nullable = false)
    private boolean hasCar; // 차량 소유 여부

    @Column(nullable = false)
    private boolean hasDementiaTraining;    // 치매 교육 이수 여부

    @Enumerated(EnumType.STRING)
    private CaregiverCareer career;  // 경력 기간

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

    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverLicense> licenses;    // 자격증

    @OneToOne(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CaregiverWorkCondition workCondition;  // 근무 조건

    public static Caregiver from(CaregiverRequestDto request, String encodedPassword) {
        return Caregiver.builder()
                .loginId(request.loginId())
                .password(encodedPassword)
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .description(request.description())
                .hasCar(request.hasCar())
                .hasDementiaTraining(request.hasDementiaTraining())
                .career(request.career() == null ? null : CaregiverCareer.fromString(request.career()))
                .toiletingAssistance(request.getToiletingAssistanceEnum())
                .feedingAssistance(request.getFeedingAssistanceEnum())
                .mobilityAssistance(request.getMobilityAssistanceEnum())
                .dailyLivingAssistance(request.getDailyLivingAssistanceEnum())
                .build();
    }

    public void setLicenses(List<CaregiverLicense> licenses) {
        this.licenses = licenses;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
