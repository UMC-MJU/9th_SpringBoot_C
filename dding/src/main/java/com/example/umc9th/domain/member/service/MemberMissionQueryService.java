package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

public interface MemberMissionQueryService {
    Page<MemberMission> getMyMissionList(Long memberId, Integer page);
}
