package com.example.umc.domain.member.controller;

import com.example.umc.domain.member.dto.request.MemberRequestDto;
import com.example.umc.domain.member.dto.response.MemberResponseDto;
import com.example.umc.domain.member.service.MemberService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc.global.auth.LoginMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "회원 관련 API")
@Validated
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 API
     */
    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인합니다. 성공 시 JWT Access Token을 반환합니다."
    )
    @PostMapping("/login")
    public ApiResponse<MemberResponseDto.LoginDto> login(
            @Valid @RequestBody MemberRequestDto.LoginDto request
    ) {
        MemberResponseDto.LoginDto response = memberService.login(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    /**
     * 회원가입 API
     */
    @Operation(
            summary = "회원가입",
            description = "새로운 회원을 등록합니다. 이름, 이메일, 비밀번호, 성별, 생년월일, 주소, 선호 음식 카테고리를 입력받아 회원을 생성합니다."
    )
    @PostMapping("/signup")
    public ApiResponse<MemberResponseDto.JoinDto> signup(
            @Valid @RequestBody MemberRequestDto.JoinDto request
    ) {
        MemberResponseDto.JoinDto response = memberService.signup(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, response);
    }

    /**
     * 마이페이지 조회 API
     */
    @Operation(
            summary = "마이페이지 조회",
            description = "로그인한 회원의 마이페이지 정보를 조회합니다."
    )
    @GetMapping("/my-page")
    public ApiResponse<MemberResponseDto.MyPageDto> getMyPage(
            @Parameter(hidden = true) @LoginMemberId Long memberId
    ) {
        MemberResponseDto.MyPageDto response = memberService.getMyPage(memberId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }
}
