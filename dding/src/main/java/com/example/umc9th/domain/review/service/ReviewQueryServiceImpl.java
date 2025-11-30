package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public Page<Review> getMyReviewList(Long memberId, Integer page) {
        // 프론트엔드 페이지는 1부터, 백엔드 페이지는 0부터 시작하므로 1을 빼줍니다.
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return reviewRepository.findAllByMemberId(memberId, pageRequest);
    }
}
