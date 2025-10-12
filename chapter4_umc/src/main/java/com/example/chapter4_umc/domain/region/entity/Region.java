package com.example.chapter4_umc.domain.region.entity;

import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.domain.review.entity.Review;
import com.example.chapter4_umc.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name", length = 100) // 지역 이름 (VARCHAR 100)
    private String regionName;

    // 1:N 관계: Region (1) : Store (N) (지역 하나마다 여러 가게 존재)
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Store> storeList;

    // 1:N 관계: Region (1) : Member (N) (여러 사용자가 하나의 지역을 선호 지역으로 설정 가능)
    @OneToMany(mappedBy = "preferredRegion", cascade = CascadeType.ALL)
    private List<Member> memberList;

    // 1:N 관계: Region (1) : Review (N) (한 지역에 리뷰 여러 개가 등록될 수 있음)
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Review> reviewList;

}