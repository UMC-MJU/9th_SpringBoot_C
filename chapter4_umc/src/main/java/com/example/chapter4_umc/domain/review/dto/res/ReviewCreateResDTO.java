package com.example.chapter4_umc.domain.review.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewCreateResDTO {

    private Long reviewId;
    private String message;
}
