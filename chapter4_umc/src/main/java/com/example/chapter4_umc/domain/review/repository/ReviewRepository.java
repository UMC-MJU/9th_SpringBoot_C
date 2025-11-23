package com.example.chapter4_umc.domain.review.repository;

import com.example.chapter4_umc.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
