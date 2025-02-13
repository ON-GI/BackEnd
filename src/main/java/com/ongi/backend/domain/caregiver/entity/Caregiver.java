package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.CaregiverRequestDto;
import com.ongi.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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

    @OneToOne(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CaregiverWorkCondition workCondition;  // 근무 조건

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaregiverCareer career;  // 경력 기간

    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverLicense> licenses;    // 자격증

    public static Caregiver from(CaregiverRequestDto caregiverRequestDto) {
        return Caregiver.builder()
                .loginId(caregiverRequestDto.getLoginId())
                .password(caregiverRequestDto.getPassword()) // 암호화된 비밀번호 적용
                .name(caregiverRequestDto.getName())
                .phoneNumber(caregiverRequestDto.getPhoneNumber())
                .address(caregiverRequestDto.getAddress())
                .description(caregiverRequestDto.getDescription())
                .hasCar(caregiverRequestDto.getHasCar())
                .hasDementiaTraining(caregiverRequestDto.getHasDementiaTraining())
                .career(CaregiverCareer.valueOf(caregiverRequestDto.getCareer()))
                .build();
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
