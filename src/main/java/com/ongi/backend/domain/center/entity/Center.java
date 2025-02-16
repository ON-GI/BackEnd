package com.ongi.backend.domain.center.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.center.dto.request.CenterInitializerRequestDto;
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

    @Column(nullable = false)
    private String centerCode;

    private String address;

    private LocalDate establishmentDate;

    private CenterGrade centerGrade;

    private String phoneNumber;

    private String description;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Senior> seniors;

    public static Center from(CenterInitializerRequestDto centerInitializerRequestDto) {
        return Center.builder()
                .name(centerInitializerRequestDto.getName())
                .centerCode(centerInitializerRequestDto.getCenterCode())
                .address(centerInitializerRequestDto.getAddress())
                .establishmentDate(centerInitializerRequestDto.getEstablishmentDate())
                .build();
    }
}
