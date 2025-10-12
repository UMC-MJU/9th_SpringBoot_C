package com.example.chapter4_umc.domain.member.entity;

import com.example.chapter4_umc.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 복합키는 관리하기 번거롭기 때문에 단일 PK로 단순화.

    // N:1 관계: MemberMission (N) : Member (1)
    // 사용자 한 명이 여러 개의 미션 수행 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // N:1 관계: MemberMission (N) : Mission (1)
    // 한 미션은 여러 사용자가 수행 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(length = 20) // 상태 (VARCHAR 20)
    private String status;

    @Column(length = 50) // 인증 코드 (VARCHAR 50)
    private String authCode;

    private LocalDateTime completedAt; //완료 일시 (TIMESTAMP)

    @CreationTimestamp // 등록 일시 (TIMESTAMP)
    private LocalDateTime createdAt;
}