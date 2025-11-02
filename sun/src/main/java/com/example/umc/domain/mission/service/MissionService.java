package com.example.umc.domain.mission.service;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.domain.mission.dto.response.MissionResponseDto;
import com.example.umc.domain.mission.dto.response.MissionResponseDto.AvailableMissionDto;
import com.example.umc.domain.mission.dto.response.MissionResponseDto.HomeDto;
import com.example.umc.domain.mission.dto.response.MissionResponseDto.MissionStatusDto;
import com.example.umc.domain.mission.entity.MemberMission;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.domain.mission.repository.MemberMissionRepository;
import com.example.umc.domain.mission.repository.MissionRepository;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private static final Long MISSION_GOAL = 10L;  // 고정된 목표 미션 개수

    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    /**
     * 진행중인 미션 목록 조회 (페이징)
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 진행중인 미션 목록 (페이징 정보 포함)
     */
    public Page<MissionResponseDto.MissionListDto> getInProgressMissions(Long memberId, Pageable pageable) {
        // 1. 진행중인 미션 조회 (isComplete = false)
        Page<MemberMission> memberMissions =
                memberMissionRepository.findMissionsByMemberAndStatus(memberId, false, pageable);

        // 2. Entity -> DTO 변환
        return memberMissions.map(MissionResponseDto.MissionListDto::from);
    }

    /**
     * 완료한 미션 목록 조회 (페이징)
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 완료한 미션 목록 (페이징 정보 포함)
     */
    public Page<MissionResponseDto.MissionListDto> getCompletedMissions(Long memberId, Pageable pageable) {
        // 1. 완료한 미션 조회 (isComplete = true)
        Page<MemberMission> memberMissions =
                memberMissionRepository.findMissionsByMemberAndStatus(memberId, true, pageable);

        // 2. Entity -> DTO 변환
        return memberMissions.map(MissionResponseDto.MissionListDto::from);
    }

    /**
     * 홈 화면 데이터 조회
     * - 현재 회원의 지역명
     * - 미션 달성 현황 (완료한 미션 개수 / 목표 개수)
     * - 도전 가능한 미션 목록 (페이징)
     *
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 홈 화면 DTO
     */
    public HomeDto getHomeScreen(Long memberId, Pageable pageable) {
        // 1. 회원 정보 조회 (예외처리 포함)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 회원의 지역 정보 추출
        Long baseAddressId = member.getBaseAddress().getId();
        String regionName = member.getBaseAddress().getBaseAddressName();

        // 3. 완료한 미션 개수 조회
        Long completedCount = memberMissionRepository.countCompletedMissions(memberId);

        // 4. 미션 달성 현황 DTO 생성
        MissionStatusDto missionStatus = MissionStatusDto.builder()
                .completedCount(completedCount)
                .totalGoal(MISSION_GOAL)
                .displayText(completedCount + "/" + MISSION_GOAL)
                .build();

        // 5. 도전 가능한 미션 목록 조회 (페이징)
        // - 회원의 지역에 있는 미션
        // - 아직 도전하지 않은 미션 (MemberMission에 없는 미션)
        // - deadline 오름차순 정렬 (마감일 임박순)
        Page<Mission> missions = missionRepository.findAvailableMissionsByRegion(
                memberId, baseAddressId, pageable
        );

        // 6. Mission Entity -> AvailableMissionDto 변환
        Page<AvailableMissionDto> availableMissions =
                missions.map(AvailableMissionDto::from);

        // 7. 최종 응답 DTO 반환
        return HomeDto.builder()
                .regionName(regionName)
                .missionStatus(missionStatus)
                .availableMissions(availableMissions)
                .build();
    }
}
