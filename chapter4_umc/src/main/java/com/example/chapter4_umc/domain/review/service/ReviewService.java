package com.example.chapter4_umc.domain.review.service;

import com.example.chapter4_umc.domain.review.dto.ReviewDto;
import com.example.chapter4_umc.domain.review.dto.ReviewSearchCondition;
import com.example.chapter4_umc.domain.review.repository.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepositoryCustom reviewRepositoryCustom;

    public List<ReviewDto> findMyReviews(Long memberId, ReviewSearchCondition condition) {
        return reviewRepositoryCustom.findMyReviewsWithFilter(memberId, condition);
    }

}
