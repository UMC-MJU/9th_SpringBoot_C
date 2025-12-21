package com.example.chapter4_umc.domain.mission.controller;

import com.example.chapter4_umc.domain.mission.dto.MissionJoinResDTO;
import com.example.chapter4_umc.domain.mission.service.MissionService;
import com.example.chapter4_umc.global.apiPayload.ApiResponse;
import com.example.chapter4_umc.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
@Tag(name = "Mission", description = "미션 도전 API")
public class MissionController {

    private final MissionService missionService;

    private Long getLoggedInMemberId() {
        return 1L; // 하드코딩
    }

    @Operation(summary = "미션 도전하기", description = "특정 미션을 도전합니다.")
    @PostMapping("/{missionId}/join")
    public ApiResponse<MissionJoinResDTO> joinMission(
            @PathVariable Long missionId
    ) {
        MissionJoinResDTO res = missionService.joinMission(missionId, getLoggedInMemberId());

        return ApiResponse.onSuccess(
                GeneralSuccessCode.MISSION_JOIN,
                res
        );
    }
}





