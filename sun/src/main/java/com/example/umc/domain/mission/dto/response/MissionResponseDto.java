package com.example.umc.domain.mission.dto.response;

import com.example.umc.domain.mission.entity.MemberMission;
import com.example.umc.domain.mission.entity.Mission;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MissionResponseDto {

    /**
     * 미션 도전하기 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ChallengeMissionDto {
        private Long memberMissionId;
        private Long missionId;
        private String restaurantName;
        private String missionCondition;
        private LocalDateTime deadline;
        private Integer missionPoint;
        private String status;              // "진행중"
        private String createdAt;           // "2025.11.22" 형식

        /**
         * MemberMission Entity -> ChallengeMissionDto 변환
         */
        public static ChallengeMissionDto from(MemberMission memberMission) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            Mission mission = memberMission.getMission();

            return ChallengeMissionDto.builder()
                    .memberMissionId(memberMission.getId())
                    .missionId(mission.getId())
                    .restaurantName(mission.getRestaurant().getRestaurantName())
                    .missionCondition(mission.getMissionCondition())
                    .deadline(mission.getDeadline())
                    .missionPoint(mission.getMissionPoint())
                    .status("진행중")
                    .createdAt(memberMission.getCreatedAt().format(formatter))
                    .build();
        }
    }

    /**
     * 회원의 미션 목록 DTO (진행중/완료)
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MissionListDto {
        private Long missionId;
        private String missionCondition;     // 미션 조건
        private LocalDateTime deadline;      // 마감기한
        private Integer missionPoint;        // 포인트
        private String restaurantName;       // 레스토랑 이름
        private String status;               // "진행중" or "완료"

        // Entity -> DTO 변환
        public static MissionListDto from(MemberMission memberMission) {
            Mission mission = memberMission.getMission();
            return MissionListDto.builder()
                    .missionId(mission.getId())
                    .missionCondition(mission.getMissionCondition())
                    .deadline(mission.getDeadline())
                    .missionPoint(mission.getMissionPoint())
                    .restaurantName(mission.getRestaurant().getRestaurantName())
                    .status(memberMission.getIsComplete() ? "완료" : "진행중")
                    .build();
        }
    }

    /**
     * 홈 화면 전체 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class HomeDto {
        private String regionName;                              // 현재 지역명
        private MissionStatusDto missionStatus;                  // 미션 달성 현황
        private Page<AvailableMissionDto> availableMissions;    // 도전 가능한 미션 목록
    }

    /**
     * 미션 달성 현황 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MissionStatusDto {
        private Long completedCount;    // 완료한 미션 개수
        private Long totalGoal;         // 목표 미션 개수 (고정: 10)
        private String displayText;     // 화면 표시용 텍스트 (예: "7/10")
    }

    /**
     * 도전 가능한 미션 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AvailableMissionDto {
        private Long missionId;
        private String restaurantName;
        private String missionCondition;
        private LocalDateTime deadline;
        private Integer missionPoint;

        /**
         * Mission Entity -> AvailableMissionDto 변환
         * @param mission 미션 엔티티
         * @return AvailableMissionDto
         */
        public static AvailableMissionDto from(Mission mission) {
            return AvailableMissionDto.builder()
                    .missionId(mission.getId())
                    .restaurantName(mission.getRestaurant().getRestaurantName())
                    .missionCondition(mission.getMissionCondition())
                    .deadline(mission.getDeadline())
                    .missionPoint(mission.getMissionPoint())
                    .build();
        }
    }

    /**
     * 미션 완료 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CompleteMissionDto {
        private Long memberMissionId;
        private Long missionId;
        private String status;              // "완료"
        private String completedAt;         // "2025.11.30" 형식

        /**
         * MemberMission Entity -> CompleteMissionDto 변환
         */
        public static CompleteMissionDto from(MemberMission memberMission) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return CompleteMissionDto.builder()
                    .memberMissionId(memberMission.getId())
                    .missionId(memberMission.getMission().getId())
                    .status("완료")
                    .completedAt(LocalDateTime.now().format(formatter))
                    .build();
        }
    }

    /**
     * 특정 가게의 미션 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RestaurantMissionDto {
        private Long missionId;
        private String restaurantName;
        private String missionCondition;
        private LocalDateTime deadline;
        private Integer missionPoint;

        /**
         * Mission Entity -> RestaurantMissionDto 변환
         */
        public static RestaurantMissionDto from(Mission mission) {
            return RestaurantMissionDto.builder()
                    .missionId(mission.getId())
                    .restaurantName(mission.getRestaurant().getRestaurantName())
                    .missionCondition(mission.getMissionCondition())
                    .deadline(mission.getDeadline())
                    .missionPoint(mission.getMissionPoint())
                    .build();
        }
    }

    /**
     * 특정 가게의 미션 목록 응답 DTO (페이징 포함)
     */
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RestaurantMissionListDto {
        private List<RestaurantMissionDto> missions;
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean hasNext;

        /**
         * Page<Mission> -> RestaurantMissionListDto 변환
         */
        public static RestaurantMissionListDto from(Page<Mission> missionPage) {
            List<RestaurantMissionDto> missions = missionPage.getContent().stream()
                    .map(RestaurantMissionDto::from)
                    .collect(Collectors.toList());

            return RestaurantMissionListDto.builder()
                    .missions(missions)
                    .currentPage(missionPage.getNumber())
                    .totalPages(missionPage.getTotalPages())
                    .totalElements(missionPage.getTotalElements())
                    .hasNext(missionPage.hasNext())
                    .build();
        }
    }
}
