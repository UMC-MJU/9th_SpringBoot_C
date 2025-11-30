package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.converter.MemberMissionConverter;
import com.example.umc9th.domain.member.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.member.service.MemberMissionCommandService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions/{missionId}/challenge")
public class MemberMissionController {

    private final MemberMissionCommandService memberMissionCommandService;

    @PostMapping("/")
    public ApiResponse<MemberMissionResponseDTO.ChallengeResultDTO> challenge(
            @PathVariable Long missionId
    ) {
        MemberMission memberMission = memberMissionCommandService.challengeMission(missionId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberMissionConverter.toChallengeResultDTO(memberMission));
    }
}
