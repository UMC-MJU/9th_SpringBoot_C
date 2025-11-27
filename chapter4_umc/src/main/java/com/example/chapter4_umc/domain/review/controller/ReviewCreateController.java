package com.example.chapter4_umc.domain.review.controller;

import com.example.chapter4_umc.domain.review.dto.req.ReviewCreateReqDTO;
import com.example.chapter4_umc.domain.review.dto.res.ReviewCreateResDTO;
import com.example.chapter4_umc.domain.review.service.ReviewService;
import com.example.chapter4_umc.global.apiPayload.ApiResponse;
import com.example.chapter4_umc.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Review", description = "리뷰 작성 API")
public class ReviewCreateController {

    private final ReviewService reviewService;

    private Long getLoggedInMemberId() {
        return 1L;
    }

    @Operation(
            summary = "가게 리뷰 작성",
            description = "특정 가게에 리뷰를 작성합니다."
    )
    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewCreateResDTO> createReview(
            @PathVariable Long storeId,
            @RequestBody ReviewCreateReqDTO req
    ) {
        req.setStoreId(storeId);
        req.setMemberId(getLoggedInMemberId());

        ReviewCreateResDTO response = reviewService.createReview(req);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REVIEW_CREATED,
                response
        );
    }
}
