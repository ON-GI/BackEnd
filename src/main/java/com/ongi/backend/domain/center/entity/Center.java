package com.ongi.backend.domain.center.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.center.dto.request.CenterInitializerRequestDto;
import com.ongi.backend.domain.center.dto.request.CenterRequestDto;
import com.ongi.backend.domain.center.entity.enums.CenterGrade;
import com.ongi.backend.domain.center.entity.enums.CenterStatus;
import com.ongi.backend.domain.centerstaff.entity.CenterStaff;
import com.ongi.backend.domain.senior.entity.Senior;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE center SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
@Table(name = "center")
public class Center extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private CenterStatus centerStatus;

    private String contact;

    private String email;

    private String address;

    private LocalDate establishmentDate;

    private CenterGrade centerGrade;

    private String description;

    private Boolean hasVehicle;

    private String centerCode;

    private String profileImageUrl;

    private String centerDocumentUrl;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CenterStaff> centerStaffs;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Senior> seniors;

    public static Center from(CenterInitializerRequestDto centerInitializerRequestDto) {
        return Center.builder()
                .name(centerInitializerRequestDto.getName())
                .centerStatus(CenterStatus.NOT_VERIFIED)
                .address(centerInitializerRequestDto.getAddress())
                .establishmentDate(centerInitializerRequestDto.getEstablishmentDate())
                .build();
    }

    public void updateCenterInfo(
            CenterRequestDto requestDto) {

        this.contact = requestDto.contact();
        this.email = requestDto.email();
        this.centerGrade = requestDto.centerGrade();
        this.description = requestDto.description();
        this.hasVehicle = requestDto.hasVehicle();
    }

    public void updateCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public void updateCenterStatus(CenterStatus centerStatus) {
        this.centerStatus = centerStatus;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateCenterDocumentUrl(String centerDocumentUrl) {
        this.centerDocumentUrl = centerDocumentUrl;
    }
}
