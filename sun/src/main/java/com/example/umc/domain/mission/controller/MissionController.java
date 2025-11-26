package com.example.umc.domain.mission.controller;

import com.example.umc.domain.mission.dto.request.MissionRequestDto;
import com.example.umc.domain.mission.dto.response.MissionResponseDto;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc.global.auth.LoginMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 미션 Controller
 *
 * 검증 전략:
 * - @Validated (클래스 레벨): @RequestParam 파라미터 검증 (AOP 기반, ConstraintViolationException)
 * - @Valid (메서드 파라미터): @RequestBody DTO 객체 검증 (ArgumentResolver 기반, MethodArgumentNotValidException)
 */
@Tag(name = "미션", description = "미션 관련 API")
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
    @Operation(
            summary = "미션 도전하기",
            description = "가게의 미션에 도전합니다. 미션 ID를 입력받아 현재 로그인한 회원의 도전 중인 미션 목록에 추가합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "미션 도전 성공",
                    content = @Content(schema = @Schema(implementation = MissionResponseDto.ChallengeMissionDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 입력값 (미션 ID 누락, 음수 또는 0)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회원 또는 미션을 찾을 수 없음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "이미 도전 중인 미션"
            )
    })
    @PostMapping
    public ApiResponse<MissionResponseDto.ChallengeMissionDto> challengeMission(
            @Parameter(hidden = true) @LoginMemberId Long memberId,
            @Valid @RequestBody MissionRequestDto.ChallengeMissionDto request) {

        MissionResponseDto.ChallengeMissionDto response =
                missionService.challengeMission(memberId, request);

        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response);
    }
}
