package com.example.chapter4_umc.domain.review.dto.res;

import com.example.chapter4_umc.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewSimpleResDTO {

    private Long reviewId;
    private Integer rating;
    private String content;
    private String imageUrl;
    private String createdAt;
}
