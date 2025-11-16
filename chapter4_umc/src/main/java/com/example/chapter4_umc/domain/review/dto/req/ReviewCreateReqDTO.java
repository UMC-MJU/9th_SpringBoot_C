package com.example.chapter4_umc.domain.review.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateReqDTO {

    private Integer rating;
    private String content;
    private String imageUrl;

    private Long memberId;
    private Long StoreId;
    private Long regionId;
}
