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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * 내가 진행 중인 미션 목록 조회 API
     * GET /api/v1/members/me/missions/in-progress
     *
     * @param memberId 현재 로그인한 회원 ID (헤더에서 자동 추출)
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param size 페이지 크기 (1~100, 기본값: 10)
     * @return 진행 중인 미션 목록 (페이징 정보 포함)
     */
    @Operation(
            summary = "내가 진행 중인 미션 목록 조회",
            description = "현재 로그인한 회원이 진행 중인 미션 목록을 조회합니다. 페이징을 지원하며, 최근 도전한 순서로 정렬됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "미션 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = MissionResponseDto.MissionListDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 페이징 파라미터 (페이지 번호 음수, 크기 범위 초과 등)"
            )
    })
    @GetMapping("/in-progress")
    public ApiResponse<org.springframework.data.domain.Page<MissionResponseDto.MissionListDto>> getInProgressMissions(
            @Parameter(hidden = true) @LoginMemberId Long memberId,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기 (1~100)", example = "10")
            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다.")
            @RequestParam(defaultValue = "10") int size) {

        // Pageable 생성
        Pageable pageable = PageRequest.of(page, size);

        // 서비스 호출
        org.springframework.data.domain.Page<MissionResponseDto.MissionListDto> response =
                missionService.getInProgressMissions(memberId, pageable);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 진행 중인 미션을 완료로 변경 API
     * PATCH /api/v1/members/me/missions/{missionId}/complete
     *
     * @param memberId 현재 로그인한 회원 ID (헤더에서 자동 추출)
     * @param missionId 완료할 미션 ID (Path Variable)
     * @return 완료된 미션 정보
     */
    @Operation(
            summary = "진행 중인 미션 완료하기",
            description = "현재 로그인한 회원이 진행 중인 미션을 완료 상태로 변경합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "미션 완료 성공",
                    content = @Content(schema = @Schema(implementation = MissionResponseDto.CompleteMissionDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회원의 미션을 찾을 수 없음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "이미 완료된 미션"
            )
    })
    @PatchMapping("/{missionId}/complete")
    public ApiResponse<MissionResponseDto.CompleteMissionDto> completeMission(
            @Parameter(hidden = true) @LoginMemberId Long memberId,
            @Parameter(description = "완료할 미션 ID", example = "1")
            @PathVariable Long missionId) {

        // 서비스 호출
        MissionResponseDto.CompleteMissionDto response =
                missionService.completeMission(memberId, missionId);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 특정 가게의 미션 목록 조회 API
     * GET /api/v1/restaurants/{restaurantId}/missions
     *
     * @param restaurantId 가게 ID (Path Variable)
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param size 페이지 크기 (1~100, 기본값: 10)
     * @return 가게의 미션 목록 (페이징 정보 포함)
     */
    @Operation(
            summary = "특정 가게의 미션 목록 조회",
            description = "특정 가게에 등록된 미션 목록을 조회합니다. 페이징을 지원하며, 마감일 임박순으로 정렬됩니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "미션 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = MissionResponseDto.RestaurantMissionListDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 페이징 파라미터 (페이지 번호 음수, 크기 범위 초과 등)"
            )
    })
    @GetMapping("/restaurants/{restaurantId}/missions")
    public ApiResponse<MissionResponseDto.RestaurantMissionListDto> getMissionsByRestaurant(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable Long restaurantId,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기 (1~100)", example = "10")
            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다.")
            @RequestParam(defaultValue = "10") int size) {

        // Pageable 생성
        Pageable pageable = PageRequest.of(page, size);

        // 서비스 호출
        MissionResponseDto.RestaurantMissionListDto response =
                missionService.getMissionsByRestaurant(restaurantId, pageable);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }
}
