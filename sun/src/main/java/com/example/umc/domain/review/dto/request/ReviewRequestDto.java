package com.example.umc.domain.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

public class ReviewRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateReviewDto {
        @NotNull(message = "회원 ID는 필수입니다.")
        private Long memberId;

        @NotNull(message = "레스토랑 ID는 필수입니다.")
        private Long restaurantId;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        private String reviewContent;

        @NotNull(message = "별점은 필수입니다.")
        @DecimalMin(value = "0.0", message = "별점은 0.0 이상이어야 합니다.")
        @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다.")
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
