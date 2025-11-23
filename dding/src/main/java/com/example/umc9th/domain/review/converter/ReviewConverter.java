package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.dto.ReviewRequestDTO;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Store;
import java.time.LocalDateTime;

public class ReviewConverter {

    public static ReviewResponseDTO.CreateResultDTO toCreateResultDTO(Review review){
        return ReviewResponseDTO.CreateResultDTO.builder()
                .reviewId(review.getReviewId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static Review toReview(ReviewRequestDTO.CreateDTO request, Member member, Store store){
        return Review.builder()
                .content(request.getContent())
                .star(request.getStar())
                .member(member)
                .store(store)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
