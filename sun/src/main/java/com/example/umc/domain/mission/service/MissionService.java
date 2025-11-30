package com.example.umc.domain.mission.service;

import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.domain.mission.dto.request.MissionRequestDto;
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
     * 미션 도전하기
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @param request 미션 도전 요청 DTO (missionId 포함)
     * @return 도전한 미션 정보
     */
    @Transactional
    public MissionResponseDto.ChallengeMissionDto challengeMission(
            Long memberId, MissionRequestDto.ChallengeMissionDto request) {

        // 1. 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 미션 조회
        Mission mission = missionRepository.findById(request.getMissionId())
                .orElseThrow(() -> new CustomException(ErrorCode.MISSION_NOT_FOUND));

        // 3. 중복 도전 체크
        if (memberMissionRepository.existsByMemberIdAndMissionId(memberId, request.getMissionId())) {
            throw new CustomException(ErrorCode.ALREADY_CHALLENGING_MISSION);
        }

        // 4. MemberMission 엔티티 생성 (isComplete = false: 진행중)
        MemberMission memberMission = MemberMission.builder()
                .member(member)
                .mission(mission)
                .isComplete(false)
                .build();

        // 5. 저장
        MemberMission savedMemberMission = memberMissionRepository.save(memberMission);

        // 6. Entity -> DTO 변환하여 반환
        return MissionResponseDto.ChallengeMissionDto.from(savedMemberMission);
    }

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

    /**
     * 특정 가게의 미션 목록 조회 (페이징)
     * @param restaurantId 가게 ID
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 가게의 미션 목록 (페이징 정보 포함)
     */
    public MissionResponseDto.RestaurantMissionListDto getMissionsByRestaurant(Long restaurantId, Pageable pageable) {
        // 1. 가게의 미션 목록 조회 (페이징)
        Page<Mission> missionPage = missionRepository.findByRestaurantId(restaurantId, pageable);

        // 2. Entity -> DTO 변환
        return MissionResponseDto.RestaurantMissionListDto.from(missionPage);
    }

    /**
     * 진행 중인 미션을 완료로 변경
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @param missionId 완료할 미션 ID
     * @return 완료된 미션 정보
     */
    @Transactional
    public MissionResponseDto.CompleteMissionDto completeMission(Long memberId, Long missionId) {
        // 1. MemberMission 조회
        MemberMission memberMission = memberMissionRepository.findByMemberIdAndMissionId(memberId, missionId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_MISSION_NOT_FOUND));

        // 2. 이미 완료된 미션인지 확인
        if (memberMission.getIsComplete()) {
            throw new CustomException(ErrorCode.MISSION_ALREADY_COMPLETED);
        }

        // 3. 미션 완료 처리 (dirty checking으로 자동 업데이트)
        memberMission.complete();

        // 4. Entity -> DTO 변환하여 반환
        return MissionResponseDto.CompleteMissionDto.from(memberMission);
    }
}
