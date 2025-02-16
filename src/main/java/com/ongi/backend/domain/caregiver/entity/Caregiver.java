package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverSignupRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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

    @Column(nullable = false)
    private boolean hasCar; // 차량 소유 여부

    private String profileImageUrl; // 프로필 사진 경로

    @OneToOne(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CaregiverInformation caregiverInformation;  // 근무 조건

    @OneToOne(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CaregiverOptional caregiverOptional;  // 근무 조건

    @OneToOne(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CaregiverWorkCondition workCondition;  // 근무 조건

    public static Caregiver from(CaregiverSignupRequestDto request, String encodedPassword) {
        return Caregiver.builder()
                .loginId(request.loginId())
                .password(encodedPassword)
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .hasCar(request.hasCar())
                .build();
    }

    public void updateCaregiverInformation(CaregiverInformation caregiverInformation) {
        this.caregiverInformation = caregiverInformation;
    }

    public void updateCaregiverOptional(CaregiverOptional caregiverOptional) {
        this.caregiverOptional = caregiverOptional;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
