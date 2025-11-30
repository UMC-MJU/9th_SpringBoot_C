package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.entity.Mission;

import java.time.LocalDateTime;

public class MemberMissionConverter {

    public static MemberMission toMemberMission(Member member, Mission mission) {
        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .isComplete(false)
                .build();
    }

    public static MemberMissionResponseDTO.ChallengeResultDTO toChallengeResultDTO(MemberMission memberMission) {
        // MemberMission 엔티티에는 createdAt이 없으므로, 현재 시간을 사용합니다.
        return MemberMissionResponseDTO.ChallengeResultDTO.builder()
                .memberMissionId(memberMission.getMemberMissionId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
