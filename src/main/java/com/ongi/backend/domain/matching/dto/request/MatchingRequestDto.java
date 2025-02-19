package com.ongi.backend.domain.matching.dto.request;

        import jakarta.validation.Valid;
        import jakarta.validation.constraints.NotNull;

        import java.util.List;

public record MatchingRequestDto (

        @NotNull(message = "어르신 ID는 필수입니다.")
        Long seniorId,

        @NotNull(message = "어르신 ID는 필수입니다.")
        Long caregiverId,

        MatchingConditionRequestDto matchingConditionRequestDto,

        @NotNull(message = "캐어 항목은 최소 1개 이상 선택해야 합니다.")
        List<String> careDetails,

        @Valid
        @NotNull(message = "캐어 시간대는 최소 1개 이상 필요합니다.")
        List<MatchingCareTimeRequestDto> careTimes
) { }
