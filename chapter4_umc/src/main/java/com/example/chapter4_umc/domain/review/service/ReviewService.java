package com.example.chapter4_umc.domain.review.service;

import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.domain.member.repository.MemberRepository;
import com.example.chapter4_umc.domain.region.entity.Region;
import com.example.chapter4_umc.domain.region.repository.RegionRepository;
import com.example.chapter4_umc.domain.review.converter.ReviewConverter;
import com.example.chapter4_umc.domain.review.dto.ReviewDto;
import com.example.chapter4_umc.domain.review.dto.ReviewSearchCondition;
import com.example.chapter4_umc.domain.review.dto.req.ReviewCreateReqDTO;
import com.example.chapter4_umc.domain.review.dto.res.ReviewCreateResDTO;
import com.example.chapter4_umc.domain.review.entity.Review;
import com.example.chapter4_umc.domain.review.repository.ReviewRepository;
import com.example.chapter4_umc.domain.review.repository.ReviewRepositoryCustom;
import com.example.chapter4_umc.domain.store.entity.Store;
import com.example.chapter4_umc.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final ReviewRepositoryCustom reviewRepositoryCustom;

    @Transactional
    public ReviewCreateResDTO createReview(ReviewCreateReqDTO req) {

        Member member = memberRepository.findById(req.getMemberId()).orElseThrow();
        Store store = storeRepository.findById(req.getStoreId()).orElseThrow();
        Region region = regionRepository.findById(req.getRegionId()).orElseThrow();

        Review review = Review.builder()
                .rating(req.getRating())
                .content(req.getContent())
                .imageUrl(req.getImageUrl())
                .member(member)
                .store(store)
                .region(region)
                .build();

        reviewRepository.save(review);

        return ReviewConverter.toCreateDTO(review);
    }

    public List<ReviewDto> findMyReviews(Long memberId, ReviewSearchCondition condition) {
        return reviewRepositoryCustom.findMyReviewsWithFilter(memberId, condition);
    }

}
