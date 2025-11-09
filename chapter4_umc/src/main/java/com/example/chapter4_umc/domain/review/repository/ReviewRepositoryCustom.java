package com.example.chapter4_umc.domain.review.repository;

import com.example.chapter4_umc.domain.review.dto.ReviewDto;
import com.example.chapter4_umc.domain.review.dto.ReviewSearchCondition;
import com.example.chapter4_umc.domain.review.entity.Review;

import java.util.List;
public interface ReviewRepositoryCustom {
    List<ReviewDto> findMyReviewsWithFilter(Long memberId, ReviewSearchCondition condition);
}

