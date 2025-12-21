package com.example.chapter4_umc.domain.member.dto;

import com.example.chapter4_umc.domain.member.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResDTO {

    public record JoinResultDTO(
            Long memberId,
            String name,
            String email,
            Gender gender,
            String address,
            LocalDate birthDate,
            String phoneNumber,
            LocalDateTime createdAt
    ) {}

    public record MemberInfoDTO(
            Long memberId,
            String name,
            String email,
            Gender gender,
            String address,
            LocalDate birthDate,
            String phoneNumber
    ) {}

    @Builder
    public record LoginDTO(
            Long memberId,
            String accessToken
    ) {}
}
