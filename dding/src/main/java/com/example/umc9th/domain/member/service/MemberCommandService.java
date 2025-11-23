package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.entity.Member;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinDto request);
}
