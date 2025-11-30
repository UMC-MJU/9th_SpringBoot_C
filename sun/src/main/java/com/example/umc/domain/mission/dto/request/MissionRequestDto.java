package com.example.umc.domain.mission.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

public class MissionRequestDto {

    /**
     * 미션 도전하기 요청 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChallengeMissionDto {
        @NotNull(message = "미션 ID는 필수입니다.")
        @Positive(message = "미션 ID는 양수여야 합니다.")
        private Long missionId;
    }
}
