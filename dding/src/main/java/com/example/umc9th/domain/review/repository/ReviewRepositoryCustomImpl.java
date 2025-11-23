package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findReviewsByStoreId(Long storeId) {
        return queryFactory
                .selectFrom(QReview.review)
                .where(QReview.review.store.storeId.eq(storeId))
                .fetch();
    }
}
