package com.example.chapter4_umc.domain.member.dto;

import com.example.chapter4_umc.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MemberReqDTO {

    public record JoinDTO(

            @NotBlank
            String name,

            @Email
            @NotBlank
            String email,

            @NotBlank
            String password,

            @NotNull
            Gender gender,

            @NotBlank
            String address,

            @NotNull
            LocalDate birthDate,

            String phoneNumber


    ) {}

    public record LoginDTO(
            String email,
            String password
    ) {}
}
