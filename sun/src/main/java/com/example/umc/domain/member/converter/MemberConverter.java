package com.example.umc.domain.member.converter;

import com.example.umc.domain.baseaddress.entity.BaseAddress;
import com.example.umc.domain.member.dto.request.MemberRequestDto;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.global.auth.Role;

public class MemberConverter {

    /**
     * DTO, 암호화된 비밀번호, Role, BaseAddress -> Member 엔티티 변환
     * @param dto 회원가입 요청 DTO
     * @param encodedPassword 암호화된 비밀번호
     * @param role 사용자 권한
     * @param baseAddress 기본 주소 엔티티
     * @return Member 엔티티
     */
    public static Member toMember(
            MemberRequestDto.JoinDto dto,
            String encodedPassword,
            Role role,
            BaseAddress baseAddress
    ) {
        return Member.builder()
                .memberName(dto.getName())
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(role)
                .gender(dto.getGender())
                .birthDate(dto.getBirth())
                .baseAddress(baseAddress)
                .detailAddress(dto.getSpecAddress())
                .memberPoint(0) // 초기 포인트 0
                .build();
    }
}
