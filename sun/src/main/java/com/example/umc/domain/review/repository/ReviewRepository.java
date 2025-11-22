package com.example.umc.domain.review.repository;

import com.example.umc.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    // 리뷰 작성: JpaRepository의 save() 메서드 사용 (메서드 생성 방식 권장)
    // save() 메서드는 JpaRepository가 제공하는 기본 메서드
}
