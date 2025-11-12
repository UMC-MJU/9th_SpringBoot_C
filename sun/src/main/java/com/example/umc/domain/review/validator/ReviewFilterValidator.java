package com.example.umc.domain.review.validator;

import com.example.umc.domain.review.dto.request.ReviewRequestDto;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 리뷰 필터 검증을 담당하는 Validator
 * 복잡한 비즈니스 검증 로직만 처리합니다.
 * 단순 범위 검증은 Bean Validation을 사용합니다.
 */
@Component
public class ReviewFilterValidator {

    private static final BigDecimal MIN_STAR_RATING = BigDecimal.ZERO;
    private static final BigDecimal MAX_STAR_RATING = new BigDecimal("5.0");

    /**
     * 리뷰 필터 검증 (복잡한 비즈니스 로직만)
     */
    public void validate(ReviewRequestDto.ReviewFilterDto filter) {
        if (filter == null) {
            return;
        }

        // 별점 범위 검증
        validateStarRating(filter.getMinStarRating());
        validateStarRating(filter.getMaxStarRating());

        // 별점 관계 검증 (복잡한 비즈니스 로직)
        validateStarRatingRange(filter.getMinStarRating(), filter.getMaxStarRating());
    }

    /**
     * 개별 별점 범위 검증 (0.0 ~ 5.0)
     */
    private void validateStarRating(BigDecimal starRating) {
        if (starRating == null) {
            return;
        }

        if (starRating.compareTo(MIN_STAR_RATING) < 0 || starRating.compareTo(MAX_STAR_RATING) > 0) {
            throw new CustomException(ErrorCode.INVALID_STAR_RATING);
        }
    }

    /**
     * 별점 범위 관계 검증 (minStarRating <= maxStarRating)
     */
    private void validateStarRatingRange(BigDecimal minStarRating, BigDecimal maxStarRating) {
        if (minStarRating == null || maxStarRating == null) {
            return;
        }

        if (minStarRating.compareTo(maxStarRating) > 0) {
            throw new CustomException(ErrorCode.INVALID_STAR_RATING);
        }
    }
}
