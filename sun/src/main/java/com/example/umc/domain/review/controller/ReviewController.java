package com.example.umc.domain.review.controller;

import com.example.umc.domain.review.dto.request.ReviewRequestDto;
import com.example.umc.domain.review.dto.response.ReviewResponseDto;
import com.example.umc.domain.review.service.ReviewService;
import com.example.umc.domain.review.validator.ReviewFilterValidator;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 리뷰 Controller
 *
 * 검증 전략:
 * - @Validated (클래스 레벨): @RequestParam 파라미터 검증 (AOP 기반, ConstraintViolationException)
 * - @Valid (메서드 파라미터): @RequestBody DTO 객체 검증 (ArgumentResolver 기반, MethodArgumentNotValidException)
 */
@Tag(name = "리뷰", description = "리뷰 관련 API")
@Validated  // @RequestParam 검증을 위한 AOP 활성화
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewFilterValidator reviewFilterValidator;

    /**
     * 리뷰 작성 API
     * @Valid: DTO 객체 내부의 검증 어노테이션 활성화 (ArgumentResolver에서 처리)
     */
    @Operation(
            summary = "리뷰 작성",
            description = "가게에 대한 리뷰를 작성합니다. 회원 ID, 레스토랑 ID, 리뷰 내용, 별점을 입력받아 리뷰를 생성합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "리뷰 작성 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDto.ReviewCreateDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 입력값 (필수값 누락, 별점 범위 초과 등)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회원 또는 레스토랑을 찾을 수 없음"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto.ReviewCreateDto>> createReview(
            @Valid @RequestBody ReviewRequestDto.CreateReviewDto request) {
        ReviewResponseDto.ReviewCreateDto response = reviewService.createReview(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response));
    }

    /**
     * 내가 작성한 리뷰 조회 API (가게별, 별점별 필터링)
     * @param memberId 현재 로그인한 회원 ID (헤더에서 자동 추출)
     * @param restaurantName 가게 이름 (선택)
     * @param minStarRating 최소 별점 (선택)
     * @param maxStarRating 최대 별점 (선택)
     * @param page 페이지 번호 (기본값: 0, Bean Validation으로 자동 검증)
     * @param size 페이지 크기 (기본값: 10, Bean Validation으로 자동 검증)
     * @return 필터링된 리뷰 목록
     */
    @Operation(
            summary = "내가 작성한 리뷰 조회",
            description = "현재 로그인한 회원이 작성한 리뷰 목록을 조회합니다. 가게 이름, 별점 범위로 필터링할 수 있으며, 페이징을 지원합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "리뷰 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDto.MyReviewListDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 페이징 파라미터 (페이지 번호 음수, 크기 범위 초과 등)"
            )
    })
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ReviewResponseDto.MyReviewListDto>> getMyReviews(
            @Parameter(hidden = true) @LoginMemberId Long memberId,
            @Parameter(description = "가게 이름 (선택)", example = "맛있는 식당")
            @RequestParam(required = false) String restaurantName,
            @Parameter(description = "최소 별점 (선택)", example = "3.0")
            @RequestParam(required = false) BigDecimal minStarRating,
            @Parameter(description = "최대 별점 (선택)", example = "5.0")
            @RequestParam(required = false) BigDecimal maxStarRating,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기 (1~100)", example = "10")
            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다.")
            @RequestParam(defaultValue = "10") int size) {

        // 필터 DTO 생성
        ReviewRequestDto.ReviewFilterDto filter = ReviewRequestDto.ReviewFilterDto.builder()
                .restaurantName(restaurantName)
                .minStarRating(minStarRating)
                .maxStarRating(maxStarRating)
                .build();

        // 복잡한 비즈니스 검증은 Validator로 위임 (별점 관계 검증 등)
        reviewFilterValidator.validate(filter);

        // Pageable 생성
        Pageable pageable = PageRequest.of(page, size);

        // 서비스 호출
        ReviewResponseDto.MyReviewListDto response = reviewService.getMyReviews(memberId, filter, pageable);

        return ResponseEntity.ok(ApiResponse.onSuccess(GeneralSuccessCode.OK, response));
    }
}
