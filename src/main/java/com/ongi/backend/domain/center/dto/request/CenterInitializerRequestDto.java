package com.ongi.backend.domain.center.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@NoArgsConstructor
public class CenterInitializerRequestDto {

    private String name;

    private String address;

    private LocalDate establishmentDate;

    @Builder
    public CenterInitializerRequestDto(String name, String address, LocalDate establishmentDate) {
        this.name = name;
        this.address = address;
        this.establishmentDate = establishmentDate;
    }
}
