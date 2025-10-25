package com.example.umc.domain.review.service;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.domain.restaurant.entity.Restaurant;
import com.example.umc.domain.restaurant.repository.RestaurantRepository;
import com.example.umc.domain.review.dto.request.ReviewRequestDto;
import com.example.umc.domain.review.dto.response.ReviewResponseDto;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.review.repository.ReviewRepository;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    /**
     * 리뷰 작성 (메서드 생성 방식 - save() 사용)
     */
    @Transactional
    public ReviewResponseDto.ReviewCreateDto createReview(ReviewRequestDto.CreateReviewDto request) {
        // 1. Member 조회
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. Restaurant 조회
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 3. Review 엔티티 생성
        Review review = Review.builder()
                .member(member)
                .restaurant(restaurant)
                .reviewContent(request.getReviewContent())
                .starRating(request.getStarRating())
                .build();

        // 4. 리뷰 저장 (메서드 생성 방식 - JpaRepository의 save() 메서드 사용)
        Review savedReview = reviewRepository.save(review);

        // 5. Entity -> DTO 변환하여 반환
        return ReviewResponseDto.ReviewCreateDto.from(savedReview);
    }
}
