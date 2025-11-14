package com.example.chapter4_umc.domain.mission.entity;

import com.example.chapter4_umc.domain.member.entity.MemberMission;
import com.example.chapter4_umc.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(length = 300) // 미션 설명 (VARCHAR 300)
    private String description;

    private Integer point;

    private Integer regionTotalMissions; // 지역 전체 미션 개수

    // N:1 관계: Mission (N) : Store (1)
    // 하나의 가게는 여러 미션 설정 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // 1:N 관계: Mission (1) : MemberMission (N)
    // 한 미션은 여러 사용자가 수행 가능
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<MemberMission> memberMissionList;
}