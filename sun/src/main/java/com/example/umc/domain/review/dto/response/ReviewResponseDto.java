package com.example.umc.domain.review.dto.response;

import com.example.umc.domain.review.entity.Review;
import lombok.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class ReviewResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ReviewCreateDto {
        private Long reviewId;
        private String memberName;        // 닉네임
        private BigDecimal starRating;    // 별점
        private String reviewContent;     // 작성한 글
        private String createdAt;         // 작성일자 (예: 2025.05.14)

        // Entity -> DTO 변환
        public static ReviewCreateDto from(Review review) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return ReviewCreateDto.builder()
                    .reviewId(review.getId())
                    .memberName(review.getMember().getMemberName())
                    .starRating(review.getStarRating())
                    .reviewContent(review.getReviewContent())
                    .createdAt(review.getCreatedAt().format(formatter))
                    .build();
        }
    }
}
