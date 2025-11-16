package com.example.chapter4_umc.domain.review.converter;

import com.example.chapter4_umc.domain.review.dto.res.ReviewCreateResDTO;
import com.example.chapter4_umc.domain.review.dto.res.ReviewDetailResDTO;
import com.example.chapter4_umc.domain.review.dto.res.ReviewListResDTO;
import com.example.chapter4_umc.domain.review.dto.res.ReviewSimpleResDTO;
import com.example.chapter4_umc.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewConverter {

    // 리뷰 생성 응답
    public static ReviewCreateResDTO toCreateDTO(Review review){
        return ReviewCreateResDTO.builder()
                .reviewId(review.getId())
                .message("리뷰가 성공적으로 생성되었습니다.")
                .build();
    }

    public static ReviewDetailResDTO toDetailDTO(Review review){
        return ReviewDetailResDTO.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .createdAt(review.getCreatedAt().toString())

                .memberId(review.getMember().getId())
                .memberName(review.getMember().getNickname())

                .storeId(review.getStore().getId())
                .storeName(review.getStore().getStoreName())

                .regionId(review.getRegion().getId())
                .regionName(review.getRegion().getRegionName())
                .build();
    }

    public static ReviewSimpleResDTO toSimpleDTO(Review review){
        return ReviewSimpleResDTO.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .createdAt(review.getCreatedAt().toString())
                .build();
    }
}
