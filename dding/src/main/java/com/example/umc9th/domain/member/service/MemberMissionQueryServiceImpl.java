package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.member.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public Page<MemberMission> getMyMissionList(Long memberId, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return memberMissionRepository.findAllByMemberId(memberId, pageRequest);
    }
}
