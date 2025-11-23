package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> findReviewsByStoreId(Long storeId);
}
