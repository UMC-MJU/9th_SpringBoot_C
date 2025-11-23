package com.example.chapter4_umc.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionJoinResDTO {

    private Long missionId;
    private Long memberId;
    private String status;
}
