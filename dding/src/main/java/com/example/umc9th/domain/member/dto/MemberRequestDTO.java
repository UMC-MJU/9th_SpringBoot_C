package com.example.umc9th.domain.member.dto;

import com.example.umc9th.domain.member.enums.Address;
import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.member.enums.SocialType;
import java.time.LocalDate;
import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto{
        String name;
        Gender gender;
        LocalDate birth;
        Address address;
        String detailAddress;
        String socialUid;
        SocialType socialType;
        String email;
        String phoneNumber;
    }
}
