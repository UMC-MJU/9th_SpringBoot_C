package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static MemberMissionResponseDTO.MissionChallengingDTO toMissionChallengingDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.MissionChallengingDTO.builder()
                .reward(memberMission.getMission().getPoint())
                .deadLine(memberMission.getMission().getDeadline())
                .missionSpec(memberMission.getMission().getConditional())
                .storeName(memberMission.getMission().getStore().getName())
                .build();
    }

    public static MemberMissionResponseDTO.MissionChallengingListDTO toMissionChallengingListDTO(Page<MemberMission> missionList) {
        List<MemberMissionResponseDTO.MissionChallengingDTO> missionChallengingDTOList = missionList.stream()
                .map(MemberMissionConverter::toMissionChallengingDTO).collect(Collectors.toList());

        return MemberMissionResponseDTO.MissionChallengingListDTO.builder()
                .missionList(missionChallengingDTOList)
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionChallengingDTOList.size())
                .build();
    }
}
