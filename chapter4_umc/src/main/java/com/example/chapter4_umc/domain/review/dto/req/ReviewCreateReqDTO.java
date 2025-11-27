package com.example.chapter4_umc.domain.review.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateReqDTO {


    @Schema(description = "평점 (1~5)", example = "5")
    private Integer rating;

    @Schema(description = "리뷰 내용", example = "음식이 맛있어요")
    private String content;

    @Schema(description = "이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    private Long memberId;

    private Long storeId;

    @Schema(description = "지역 ID", example = "2")
    private Long regionId;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
