package com.example.umc.domain.review.controller;

import com.example.umc.domain.review.dto.request.ReviewRequestDto;
import com.example.umc.domain.review.dto.response.ReviewResponseDto;
import com.example.umc.domain.review.service.ReviewService;
import com.example.umc.global.auth.LoginMemberId;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 API
     */
    @PostMapping
    public ResponseEntity<ReviewResponseDto.ReviewCreateDto> createReview(
            @RequestBody ReviewRequestDto.CreateReviewDto request) {
        ReviewResponseDto.ReviewCreateDto response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 내가 작성한 리뷰 조회 API (가게별, 별점별 필터링)
     * @param memberId 현재 로그인한 회원 ID (헤더에서 자동 추출)
     * @param restaurantName 가게 이름 (선택)
     * @param minStarRating 최소 별점 (선택)
     * @param maxStarRating 최대 별점 (선택)
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 필터링된 리뷰 목록
     */
    @GetMapping("/my")
    public ResponseEntity<ReviewResponseDto.MyReviewListDto> getMyReviews(
            @LoginMemberId Long memberId,
            @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) BigDecimal minStarRating,
            @RequestParam(required = false) BigDecimal maxStarRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 입력값 검증
        validateInputs(size, minStarRating, maxStarRating);

        // 필터 DTO 생성
        ReviewRequestDto.ReviewFilterDto filter = ReviewRequestDto.ReviewFilterDto.builder()
                .restaurantName(restaurantName)
                .minStarRating(minStarRating)
                .maxStarRating(maxStarRating)
                .build();

        // Pageable 생성
        Pageable pageable = PageRequest.of(page, size);

        // 서비스 호출
        ReviewResponseDto.MyReviewListDto response = reviewService.getMyReviews(memberId, filter, pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * 입력값 검증
     */
    private void validateInputs(int size, BigDecimal minStarRating, BigDecimal maxStarRating) {
        // 페이지 크기 검증 (1 ~ 100)
        if (size < 1 || size > 100) {
            throw new CustomException(ErrorCode.INVALID_PAGE_SIZE);
        }

        // 최소 별점 검증 (0.0 ~ 5.0)
        if (minStarRating != null && (minStarRating.compareTo(BigDecimal.ZERO) < 0 || minStarRating.compareTo(new BigDecimal("5.0")) > 0)) {
            throw new CustomException(ErrorCode.INVALID_STAR_RATING);
        }

        // 최대 별점 검증 (0.0 ~ 5.0)
        if (maxStarRating != null && (maxStarRating.compareTo(BigDecimal.ZERO) < 0 || maxStarRating.compareTo(new BigDecimal("5.0")) > 0)) {
            throw new CustomException(ErrorCode.INVALID_STAR_RATING);
        }

        // 최소 별점 <= 최대 별점 검증
        if (minStarRating != null && maxStarRating != null && minStarRating.compareTo(maxStarRating) > 0) {
            throw new CustomException(ErrorCode.INVALID_STAR_RATING);
        }
    }
}
