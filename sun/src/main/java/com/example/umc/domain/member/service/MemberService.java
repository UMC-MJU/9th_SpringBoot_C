package com.example.umc.domain.member.service;

import com.example.umc.domain.member.dto.response.MemberResponseDto;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 마이페이지 조회 (메서드 이름으로 쿼리 생성 - findById 사용)
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @return 마이페이지 정보 (닉네임, 이메일, 전화번호, 인증여부, 포인트)
     */
    public MemberResponseDto.MyPageDto getMyPage(Long memberId) {
        // 1. 회원 조회 (JpaRepository의 findById 메서드 사용)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. Entity -> DTO 변환
        return MemberResponseDto.MyPageDto.from(member);
    }
}
