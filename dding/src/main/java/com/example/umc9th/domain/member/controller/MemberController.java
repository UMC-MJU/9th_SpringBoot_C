package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.converter.MemberMissionConverter;
import com.example.umc9th.domain.member.dto.MemberMissionResponseDTO;
import com.example.umc9th.domain.member.dto.MemberRequestDTO;
import com.example.umc9th.domain.member.dto.MemberResponseDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.member.service.MemberCommandService;
import com.example.umc9th.domain.member.service.MemberMissionQueryService;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final ReviewQueryService reviewQueryService;
    private final MemberMissionQueryService memberMissionQueryService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody MemberRequestDTO.JoinDto request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberConverter.toJoinResultDTO(member));
    }

    @GetMapping("/reviews")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "내가 작성한 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. page 번호는 1부터 시작합니다.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요.")
    })
    public ApiResponse<ReviewResponseDTO.ReviewPreviewListDTO> getMyReviewList(@CheckPage @RequestParam(name = "page") Integer page) {
        // 사용자 ID를 1로 하드코딩
        Page<Review> reviewList = reviewQueryService.getMyReviewList(1L, page);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, ReviewConverter.toReviewPreviewListDTO(reviewList));
    }

    @GetMapping("/missions")
    @Operation(summary = "내가 진행중인 미션 목록 조회 API", description = "내가 진행중인 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. page 번호는 1부터 시작합니다.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요.")
    })
    public ApiResponse<MemberMissionResponseDTO.MissionChallengingListDTO> getMyMissionList(@CheckPage @RequestParam(name = "page") Integer page) {
        // 사용자 ID를 1로 하드코딩
        Page<MemberMission> missionList = memberMissionQueryService.getMyMissionList(1L, page);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberMissionConverter.toMissionChallengingListDTO(missionList));
    }
}
