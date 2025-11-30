package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;

public interface MemberMissionCommandService {
    MemberMission challengeMission(Long missionId);
}
