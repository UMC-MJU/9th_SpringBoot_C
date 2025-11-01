package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 메서드 이름으로 쿼리 생성
    List<Review> findByMemberMemberId(Long memberId);

    List<Review> findByStoreStoreId(Long storeId);

    List<Review> findByStarGreaterThanEqual(Float minStar);

    // @Query 사용
    @Query("SELECT AVG(r.star) FROM Review r WHERE r.store.storeId = :storeId")
    Double calculateAverageStar(@Param("storeId") Long storeId);

    @Query("SELECT r FROM Review r WHERE r.store.storeId = :storeId ORDER BY r.createdAt DESC")
    List<Review> findRecentByStore(@Param("storeId") Long storeId);

    @Query("SELECT r FROM Review r JOIN FETCH r.member WHERE r.reviewId = :reviewId")
    Optional<Review> findByIdWithMember(@Param("reviewId") Long reviewId);

    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.reviewId = :reviewId")
    Optional<Review> findByIdWithStore(@Param("reviewId") Long reviewId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.member.memberId = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.store.storeId = :storeId")
    Long countByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT r FROM Review r WHERE r.member.memberId = :memberId AND r.star >= :minStar")
    List<Review> findByMemberAndMinStar(@Param("memberId") Long memberId, @Param("minStar") Float minStar);
}
