package com.ongi.backend.entity.caregiver;

import com.ongi.backend.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE caregiver SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
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
}
