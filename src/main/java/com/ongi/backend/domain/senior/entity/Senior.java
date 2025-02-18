package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.common.enums.Gender;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.entity.enums.GradeType;
import com.ongi.backend.domain.senior.entity.enums.Residence;
import com.ongi.backend.domain.senior.entity.enums.ResidenceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE senior SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "senior")
public class Senior extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate birthDate;

    @Column
    private Integer age;

    @Column
    private Double weight;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private GradeType gradeType;

    @Column
    @Embedded
    private Residence residence;

    @Column
    @Enumerated(EnumType.STRING)
    private ResidenceType residenceType;

    @Column
    private String profileImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    @OneToOne(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private SeniorCareCondition careCondition;

    @OneToOne(mappedBy = "senior", cascade = CascadeType.ALL, orphanRemoval = true)
    private SeniorDisease seniorDisease;

    public static Senior from(SeniorRequestDto requestDto, Center center) {
        return Senior.builder()
                .name(requestDto.name())
                .birthDate(requestDto.birthDate())
                .age(requestDto.age())
                .weight(requestDto.weight())
                .gender(requestDto.gender())
                .gradeType(requestDto.gradeType())
                .residence(requestDto.residence().toEntity())
                .residenceType(requestDto.residenceType())
                .center(center)
                .build();
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateBasicInfo(SeniorRequestDto requestDto) {
        this.name = requestDto.name();
        this.birthDate = requestDto.birthDate();
        this.age = requestDto.age();
        this.weight = requestDto.weight();
        this.gender = requestDto.gender();
        this.gradeType = requestDto.gradeType();
        this.residence = requestDto.residence().toEntity();
        this.residenceType = requestDto.residenceType();
    }
}
