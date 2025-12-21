package com.example.chapter4_umc.domain.member.converter;

import com.example.chapter4_umc.domain.member.dto.MemberReqDTO;
import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.global.auth.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberConverter {

    public static Member toMember(
            MemberReqDTO.JoinDTO request,
            PasswordEncoder passwordEncoder
    ) {
        return Member.builder()
                .nickname(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .gender(request.gender())
                .birthDate(request.birthDate())
                .address(request.address())
                .phoneNumber(request.phoneNumber())
                .role(Role.USER)
                .build();
    }
}
