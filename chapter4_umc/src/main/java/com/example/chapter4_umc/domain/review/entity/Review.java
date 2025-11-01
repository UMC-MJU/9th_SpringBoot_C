package com.example.chapter4_umc.domain.review.entity;

import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.domain.store.entity.Store;
import com.example.chapter4_umc.domain.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false) // 평점 (INT, NOT NULL)
    private Integer rating;

    @Column(columnDefinition = "TEXT") // 내용 (TEXT)
    private String content;

    @Column(length = 300) // 이미지 경로 (VARCHAR 300)
    private String imageUrl;

    @CreationTimestamp // 작성 일시 (TIMESTAMP)
    private LocalDateTime createdAt;

    // N:1 관계: Review (N) : Member (1)
    // 사용자는 리뷰를 여러 개 등록할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // N:1 관계: Review (N) : Store (1)
    // 가게는 리뷰를 여러 개 등록할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // N:1 관계: Review (N) : Region (1)
    // 한 지역에 여러 개의 리뷰가 등록될 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
}