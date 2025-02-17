package com.ongi.backend.domain.center.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CenterSearchCondition {
    private String centerName;  // 센터 이름
    private String centerCode;  // 센터 코드
}
