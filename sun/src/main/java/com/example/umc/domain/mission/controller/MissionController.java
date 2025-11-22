package com.example.umc.domain.mission.controller;

import com.example.umc.domain.mission.dto.request.MissionRequestDto;
import com.example.umc.domain.mission.dto.response.MissionResponseDto;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc.global.auth.LoginMemberId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 미션 Controller
 *
 * 검증 전략:
 * - @Validated (클래스 레벨): @RequestParam 파라미터 검증 (AOP 기반, ConstraintViolationException)
 * - @Valid (메서드 파라미터): @RequestBody DTO 객체 검증 (ArgumentResolver 기반, MethodArgumentNotValidException)
 */
@Validated  // @RequestParam 검증을 위한 AOP 활성화
@RestController
@RequestMapping("/api/v1/members/me/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    /**
     * 미션 도전하기 API
     * POST /api/v1/members/me/missions
     *
     * @Valid: DTO 객체 내부의 검증 어노테이션 활성화 (ArgumentResolver에서 처리)
     * @param memberId 현재 로그인한 회원 ID (JWT에서 자동 추출)
     * @param request 미션 도전 요청 DTO (missionId 포함)
     * @return 도전한 미션 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MissionResponseDto.ChallengeMissionDto>> challengeMission(
            @LoginMemberId Long memberId,
            @Valid @RequestBody MissionRequestDto.ChallengeMissionDto request) {

        MissionResponseDto.ChallengeMissionDto response =
                missionService.challengeMission(memberId, request);

        return ResponseEntity.ok(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }
}
