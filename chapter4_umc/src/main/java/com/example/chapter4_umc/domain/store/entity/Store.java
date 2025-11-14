package com.example.chapter4_umc.domain.store.entity;

import com.example.chapter4_umc.domain.mission.entity.Mission;
import com.example.chapter4_umc.domain.review.entity.Review;
import com.example.chapter4_umc.domain.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name", length = 100) // 가게 이름 (VARCHAR 100)
    private String storeName;

    @Column(length = 300) // 주소 (VARCHAR 300)
    private String address;

    @Column(length = 50) // 카테고리 (VARCHAR 50)
    private String category;

    @Column(length = 20) // 전화번호 (VARCHAR 20)
    private String phoneNumber;

    @CreationTimestamp // 등록일시 (TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp // 수정일시 (TIMESTAMP)
    private LocalDateTime updatedAt;

    // N:1 관계: Store (N) : Region (1)
    // 지역 하나마다 여러 가게 존재
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id") // 지역 ID
    private Region region;

    // 1:N 관계: Store (1) : Mission (N)
    // 하나의 가게는 여러 미션 설정 가능
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Mission> missionList;

    // 1:N 관계: Store (1) : Review (N)
    // 가게는 리뷰를 여러 개 등록할 수 있음
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviewList;
}
