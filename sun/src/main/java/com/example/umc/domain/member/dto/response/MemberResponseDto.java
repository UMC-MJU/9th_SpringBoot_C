package com.example.umc.domain.member.dto.response;

import com.example.umc.domain.member.entity.Member;
import lombok.*;

public class MemberResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MyPageDto {
        private String memberName;          // 닉네임
        private String email;               // 이메일
        private String phoneNumber;         // 휴대전화번호
        private String phoneVerified;       // "인증" 또는 "미인증"
        private Integer memberPoint;        // 내 포인트

        // Entity -> DTO 변환
        public static MyPageDto from(Member member) {
            // phoneNumber가 null이거나 비어있으면 "미인증", 아니면 "인증"
            String phoneVerified = (member.getPhoneNumber() == null ||
                                   member.getPhoneNumber().isEmpty())
                ? "미인증"
                : "인증";

            return MyPageDto.builder()
                    .memberName(member.getMemberName())
                    .email(member.getEmail())
                    .phoneNumber(member.getPhoneNumber())
                    .phoneVerified(phoneVerified)
                    .memberPoint(member.getMemberPoint())
                    .build();
        }
    }
}
