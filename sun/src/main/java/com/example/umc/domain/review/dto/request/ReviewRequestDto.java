package com.example.umc.domain.review.dto.request;

import lombok.*;

import java.math.BigDecimal;

public class ReviewRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateReviewDto {
        private Long memberId;
        private Long restaurantId;
        private String reviewContent;
        private BigDecimal starRating;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ReviewFilterDto {
        private String restaurantName;  // 가게 이름 (optional)
        private BigDecimal minStarRating;  // 최소 별점 (optional)
        private BigDecimal maxStarRating;  // 최대 별점 (optional)
    }
}
