package com.example.chapter4_umc.domain.review.controller;

import com.example.chapter4_umc.domain.review.dto.ReviewDto;
import com.example.chapter4_umc.domain.review.dto.ReviewSearchCondition;
import com.example.chapter4_umc.domain.review.service.ReviewService;
import com.example.chapter4_umc.global.apiPayload.ApiResponse;
import com.example.chapter4_umc.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class MyReviewController {

    private final ReviewService reviewService;

    // 로그인된 사용자 ID 가져오기 (임시 하드코딩)
    private Long getLoggedInMemberId(){
        return 1L;
    }

    @GetMapping("/my")
    public ApiResponse<List<ReviewDto>> getMyReviews(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String ratingRange) {

        Long memberId = getLoggedInMemberId();

        ReviewSearchCondition condition = new ReviewSearchCondition();
        condition.setStoreName(storeName);
        condition.setRatingRange(ratingRange);

        List<ReviewDto> response = reviewService.findMyReviews(memberId, condition);

        return ApiResponse.onSuccess(
                GeneralSuccessCode.REVIEW_READ,
                response
        );
    }
}
