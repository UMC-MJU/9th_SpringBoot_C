package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.domain.Page;

public interface ReviewQueryService {
    Page<Review> getMyReviewList(Long memberId, Integer page);
}
