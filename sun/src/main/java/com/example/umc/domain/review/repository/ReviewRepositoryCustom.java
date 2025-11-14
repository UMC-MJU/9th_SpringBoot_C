package com.example.umc.domain.review.repository;

import com.example.umc.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ReviewRepositoryCustom {
    /**
     * 내가 작성한 리뷰 조회 (가게별, 별점별 필터링)
     * @param memberId 회원 ID
     * @param restaurantName 가게 이름 (optional)
     * @param minStarRating 최소 별점 (optional)
     * @param maxStarRating 최대 별점 (optional)
     * @param pageable 페이징 정보
     * @return 필터링된 리뷰 목록
     */
    Page<Review> findMyReviewsWithFilters(Long memberId, String restaurantName,
                                          BigDecimal minStarRating, BigDecimal maxStarRating,
                                          Pageable pageable);
}
