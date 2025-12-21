package com.example.chapter4_umc.domain.member.service;

import com.example.chapter4_umc.domain.member.converter.MemberConverter;
import com.example.chapter4_umc.domain.member.dto.MemberReqDTO;
import com.example.chapter4_umc.domain.member.dto.MemberResDTO;
import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.domain.member.repository.MemberRepository;
import com.example.chapter4_umc.global.security.CustomUserDetails;
import com.example.chapter4_umc.global.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public MemberResDTO.LoginDTO login(
            MemberReqDTO.@Valid LoginDTO dto
    ) {
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        if (!encoder.matches(dto.password(), member.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        CustomUserDetails userDetails = new CustomUserDetails(member);

        String accessToken = jwtUtil.createAccessToken(userDetails);

        return MemberConverter.toLoginDTO(member, accessToken);
    }
}


