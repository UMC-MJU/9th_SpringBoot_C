package com.example.umc9th.domain.review.dto;

import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class CreateDTO{
        private String content;
        private Float star;
        private Long storeId;
        private Long memberId;
    }
}
