package com.ongi.backend.domain.matching.entity.enums;

public enum MatchingStatus {
    // 거절됨
    REJECTED,

    // 요청 대기중 - 요양 보호사가 확인 안 함
    PENDING_UNREAD,

    // 요청 대기중 - 요양 보호사가 확인함
    PENDING_READ,
    // 조율중
    ADJUSTING,
    // 매칭 완료
    CONFIRMED
}
