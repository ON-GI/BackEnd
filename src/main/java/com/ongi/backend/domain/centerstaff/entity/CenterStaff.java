package com.ongi.backend.domain.centerstaff.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.common.enums.Authority;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.centerstaff.dto.request.CenterStaffSignupRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "center_staff")
public class CenterStaff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean approval;

    private Authority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    public static CenterStaff from(CenterStaffSignupRequest request, String encodedPassword, Center center) {
        return CenterStaff.builder()
                .loginId(request.loginId())
                .password(encodedPassword)
                .approval(true)
                .authority(Authority.fromString(request.authority()))
                .center(center)
                .build();
    }
}
