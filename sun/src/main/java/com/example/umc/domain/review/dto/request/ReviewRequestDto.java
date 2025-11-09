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
}
