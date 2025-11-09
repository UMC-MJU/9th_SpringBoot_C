package com.example.chapter4_umc.domain.review.repository;

import com.example.chapter4_umc.domain.review.dto.ReviewDto;
import com.example.chapter4_umc.domain.review.dto.ReviewSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.chapter4_umc.domain.review.entity.QReview.review;
import static com.example.chapter4_umc.domain.store.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomlmpI implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewDto> findMyReviewsWithFilter(Long memberId, ReviewSearchCondition condition) {
        return queryFactory
                .select(Projections.constructor(
                        ReviewDto.class,
                        review.id,
                        review.rating,
                        review.content,
                        review.store.storeName,
                        review.imageUrl
                ))
                .from(review)
                .join(review.store, store)
                .where(
                        memberIdEq(memberId),
                        storeNameEq(condition.getStoreName()),
                        ratingEq(condition.getRatingRange())
                )
                .orderBy(review.createdAt.desc())
                .fetch();
    }
    // 사용자 ID 필터링
    private BooleanExpression memberIdEq(Long memberId) {
        return review.member.id.eq(memberId);
    }

    // 가게 이름 필터링
    private BooleanExpression storeNameEq(String storeName) {
        return (storeName != null && !storeName.isEmpty()) ?
                review.store.storeName.eq(storeName) : null;
    }

    // 별점 필터링
    private BooleanExpression ratingEq(String ratingRange) {
        if (ratingRange == null || ratingRange.isEmpty()){
            return null;
        }
        try {
            int rating = Integer.parseInt(ratingRange);
            if (rating >=1 && rating <= 5 ){
                return review.rating.eq(rating);
            }
            return null; // 1~5점 범위 밖은 취급하지 않음.
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
