package com.example.umc.domain.review.repository;

import com.example.umc.domain.review.entity.QReview;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.restaurant.entity.QRestaurant;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findMyReviewsWithFilters(Long memberId, String restaurantName,
                                                 BigDecimal minStarRating, BigDecimal maxStarRating,
                                                 Pageable pageable) {
        QReview review = QReview.review;
        QRestaurant restaurant = QRestaurant.restaurant;

        // 동적 쿼리 빌드
        BooleanBuilder builder = new BooleanBuilder();

        // 필수 조건: 회원 ID
        builder.and(review.member.id.eq(memberId));

        // 선택 조건: 가게 이름 (부분 일치)
        if (restaurantName != null && !restaurantName.trim().isEmpty()) {
            builder.and(review.restaurant.restaurantName.contains(restaurantName));
        }

        // 선택 조건: 별점 범위
        if (minStarRating != null && maxStarRating != null) {
            builder.and(review.starRating.between(minStarRating, maxStarRating));
        } else if (minStarRating != null) {
            builder.and(review.starRating.goe(minStarRating));
        } else if (maxStarRating != null) {
            builder.and(review.starRating.loe(maxStarRating));
        }

        // 데이터 조회 (페이징 적용)
        List<Review> content = queryFactory
                .selectFrom(review)
                .join(review.restaurant, restaurant).fetchJoin()  // N+1 방지
                .join(review.member).fetchJoin()  // N+1 방지
                .where(builder)
                .orderBy(review.createdAt.desc())  // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회 (count 쿼리) - 필터링 조건이 restaurant 테이블을 참조하므로 join 필요
        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .join(review.restaurant, restaurant)  // restaurantName 필터링을 위한 join
                .where(builder);

        // Page 객체 생성 및 반환
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
