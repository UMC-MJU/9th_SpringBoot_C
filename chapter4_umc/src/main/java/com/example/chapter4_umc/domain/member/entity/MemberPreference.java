package com.example.chapter4_umc.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 복합키는 관리가 번거롭기 때문에 단일 PK로 단순화

    // N:1 관계: MemberPreference (N) : Member (1)
    // 한 명의 사용자가 여러 선호 음식 설정 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 선호 음식 (VARCHAR 50, NOT NULL)
    @Column(name = "preferred_food", length = 50, nullable = false)
    private String preferredFood;
}