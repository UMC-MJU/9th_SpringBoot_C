package com.example.chapter4_umc.domain.mission.service;

import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.domain.member.repository.MemberRepository;
import com.example.chapter4_umc.domain.mission.dto.MissionJoinResDTO;
import com.example.chapter4_umc.domain.mission.entity.Mission;
import com.example.chapter4_umc.domain.mission.entity.MissionProgress;
import com.example.chapter4_umc.domain.mission.repository.MissionProgressRepository;
import com.example.chapter4_umc.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionProgressRepository missionProgressRepository;
    private final MemberRepository memberRepository;

    public MissionJoinResDTO joinMission(Long missionId, Long memberId) {

        Mission mission = missionRepository.findById(missionId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        MissionProgress progress = MissionProgress.builder()
                .mission(mission)
                .member(member)
                .status("도전중")
                .build();

        missionProgressRepository.save(progress);

        return MissionJoinResDTO.builder()
                .missionId(missionId)
                .memberId(memberId)
                .status("도전중")
                .build();
    }
}
