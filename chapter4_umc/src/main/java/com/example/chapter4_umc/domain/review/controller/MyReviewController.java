package com.example.chapter4_umc.domain.review.controller;

import com.example.chapter4_umc.domain.review.dto.ReviewDto;
import com.example.chapter4_umc.domain.review.dto.ReviewSearchCondition;
import com.example.chapter4_umc.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class MyReviewController {

    private final ReviewService reviewService;

    // 로그인된 사용자 ID 가져오기
    private Long getLoggedInMemberId(){
        return 1L;
    }

    @GetMapping("/my")
    public List<ReviewDto> getMyReviews(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String ratingRange) {

        // 로그인된 사용자 ID 획득
        Long memberId = getLoggedInMemberId();

        // 검색 조건 DTO 생성
        ReviewSearchCondition condition = new ReviewSearchCondition();
        condition.setStoreName(storeName);
        condition.setRatingRange(ratingRange);

        // 서비스 호출 및 결과 반환
        return reviewService.findMyReviews(memberId, condition);
    }

}
