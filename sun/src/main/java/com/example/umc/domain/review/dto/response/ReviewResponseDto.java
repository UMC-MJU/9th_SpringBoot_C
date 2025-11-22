package com.example.umc.domain.review.dto.response;

import com.example.umc.domain.review.entity.Review;
import lombok.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MyReviewDto {
        private Long reviewId;
        private String restaurantName;    // 가게 이름
        private String memberName;        // 작성자 닉네임
        private BigDecimal starRating;    // 별점
        private String reviewContent;     // 리뷰 내용
        private String createdAt;         // 작성일자

        // Entity -> DTO 변환
        public static MyReviewDto from(Review review) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return MyReviewDto.builder()
                    .reviewId(review.getId())
                    .restaurantName(review.getRestaurant().getRestaurantName())
                    .memberName(review.getMember().getMemberName())
                    .starRating(review.getStarRating())
                    .reviewContent(review.getReviewContent())
                    .createdAt(review.getCreatedAt().format(formatter))
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MyReviewListDto {
        private List<MyReviewDto> reviews;
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean hasNext;

        // Page<Review> -> DTO 변환
        public static MyReviewListDto from(Page<Review> reviewPage) {
            List<MyReviewDto> reviews = reviewPage.getContent().stream()
                    .map(MyReviewDto::from)
                    .collect(Collectors.toList());

            return MyReviewListDto.builder()
                    .reviews(reviews)
                    .currentPage(reviewPage.getNumber())
                    .totalPages(reviewPage.getTotalPages())
                    .totalElements(reviewPage.getTotalElements())
                    .hasNext(reviewPage.hasNext())
                    .build();
        }
    }
}
