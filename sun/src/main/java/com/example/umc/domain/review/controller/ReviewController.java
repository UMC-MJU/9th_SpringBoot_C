package com.example.umc.domain.review.controller;

import com.example.umc.domain.review.dto.request.ReviewRequestDto;
import com.example.umc.domain.review.dto.response.ReviewResponseDto;
import com.example.umc.domain.review.service.ReviewService;
import com.example.umc.domain.review.validator.ReviewFilterValidator;
import com.example.umc.global.auth.LoginMemberId;
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
@Validated  // @RequestParam 검증을 위한 AOP 활성화
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewFilterValidator reviewFilterValidator;

    /**
     * 리뷰 작성 API
     * @Valid: DTO 객체 내부의 검증 어노테이션 활성화 (ArgumentResolver에서 처리)
     */
    @PostMapping
    public ResponseEntity<ReviewResponseDto.ReviewCreateDto> createReview(
            @Valid @RequestBody ReviewRequestDto.CreateReviewDto request) {
        ReviewResponseDto.ReviewCreateDto response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
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
    @GetMapping("/my")
    public ResponseEntity<ReviewResponseDto.MyReviewListDto> getMyReviews(
            @LoginMemberId Long memberId,
            @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) BigDecimal minStarRating,
            @RequestParam(required = false) BigDecimal maxStarRating,
            @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
            @RequestParam(defaultValue = "0") int page,
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

        return ResponseEntity.ok(response);
    }
}
