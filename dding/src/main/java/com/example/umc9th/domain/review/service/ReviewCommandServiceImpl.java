package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewRequestDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public Review createReview(Long storeId, ReviewRequestDTO.CreateDTO request) {
        Member member = memberRepository.findById(1L) // 하드코딩된 사용자 ID
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));
        Review newReview = ReviewConverter.toReview(request, member, store);
        return reviewRepository.save(newReview);
    }
}
