package com.example.umc.domain.mission.controller;

import com.example.umc.domain.mission.dto.request.MissionRequestDto;
import com.example.umc.domain.mission.dto.response.MissionResponseDto;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MissionController.class)
@DisplayName("미션 Controller 테스트")
class MissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MissionService missionService;

    @Test
    @DisplayName("미션 도전하기 성공")
    void challengeMission_Success() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(1L)
                .build();

        MissionResponseDto.ChallengeMissionDto response = MissionResponseDto.ChallengeMissionDto.builder()
                .memberMissionId(1L)
                .missionId(1L)
                .restaurantName("맛있는 식당")
                .missionCondition("10,000원 이상 주문하기")
                .deadline(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .missionPoint(500)
                .status("진행중")
                .createdAt("2025.11.22")
                .build();

        given(missionService.challengeMission(eq(1L), any(MissionRequestDto.ChallengeMissionDto.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")  // LoginMemberId mock
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON201"))
                .andExpect(jsonPath("$.result.memberMissionId").value(1L))
                .andExpect(jsonPath("$.result.missionId").value(1L))
                .andExpect(jsonPath("$.result.restaurantName").value("맛있는 식당"))
                .andExpect(jsonPath("$.result.missionCondition").value("10,000원 이상 주문하기"))
                .andExpect(jsonPath("$.result.missionPoint").value(500))
                .andExpect(jsonPath("$.result.status").value("진행중"));
    }

    @Test
    @DisplayName("미션 도전하기 실패 - missionId null")
    void challengeMission_Fail_MissionIdNull() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(null)  // null
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("미션 도전하기 실패 - missionId 음수")
    void challengeMission_Fail_MissionIdNegative() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(-1L)  // 음수
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("미션 도전하기 실패 - missionId 0")
    void challengeMission_Fail_MissionIdZero() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(0L)  // 0
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("미션 도전하기 실패 - 존재하지 않는 회원")
    void challengeMission_Fail_MemberNotFound() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(1L)
                .build();

        given(missionService.challengeMission(eq(999L), any(MissionRequestDto.ChallengeMissionDto.class)))
                .willThrow(new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "999")  // 존재하지 않는 회원
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("MEMBER-001"));
    }

    @Test
    @DisplayName("미션 도전하기 실패 - 존재하지 않는 미션")
    void challengeMission_Fail_MissionNotFound() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(999L)  // 존재하지 않는 미션
                .build();

        given(missionService.challengeMission(eq(1L), any(MissionRequestDto.ChallengeMissionDto.class)))
                .willThrow(new CustomException(ErrorCode.MISSION_NOT_FOUND));

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("MISSION-001"));
    }

    @Test
    @DisplayName("미션 도전하기 실패 - 이미 도전 중인 미션")
    void challengeMission_Fail_AlreadyChallenging() throws Exception {
        // given
        MissionRequestDto.ChallengeMissionDto request = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(1L)
                .build();

        given(missionService.challengeMission(eq(1L), any(MissionRequestDto.ChallengeMissionDto.class)))
                .willThrow(new CustomException(ErrorCode.ALREADY_CHALLENGING_MISSION));

        // when & then
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("MISSION-002"))
                .andExpect(jsonPath("$.message").value("이미 도전 중인 미션입니다."));
    }

    @Test
    @DisplayName("미션 도전하기 성공 - 여러 미션 순차적으로 도전")
    void challengeMission_Success_MultipleMissions() throws Exception {
        // given - 첫 번째 미션
        MissionRequestDto.ChallengeMissionDto request1 = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(1L)
                .build();

        MissionResponseDto.ChallengeMissionDto response1 = MissionResponseDto.ChallengeMissionDto.builder()
                .memberMissionId(1L)
                .missionId(1L)
                .restaurantName("첫 번째 식당")
                .missionCondition("10,000원 이상 주문하기")
                .deadline(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .missionPoint(500)
                .status("진행중")
                .createdAt("2025.11.22")
                .build();

        given(missionService.challengeMission(eq(1L), any(MissionRequestDto.ChallengeMissionDto.class)))
                .willReturn(response1);

        // when & then - 첫 번째 미션 도전
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.missionId").value(1L));

        // given - 두 번째 미션
        MissionRequestDto.ChallengeMissionDto request2 = MissionRequestDto.ChallengeMissionDto.builder()
                .missionId(2L)
                .build();

        MissionResponseDto.ChallengeMissionDto response2 = MissionResponseDto.ChallengeMissionDto.builder()
                .memberMissionId(2L)
                .missionId(2L)
                .restaurantName("두 번째 식당")
                .missionCondition("20,000원 이상 주문하기")
                .deadline(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .missionPoint(1000)
                .status("진행중")
                .createdAt("2025.11.22")
                .build();

        given(missionService.challengeMission(eq(1L), any(MissionRequestDto.ChallengeMissionDto.class)))
                .willReturn(response2);

        // when & then - 두 번째 미션 도전
        mockMvc.perform(post("/api/v1/members/me/missions")
                        .header("X-Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.missionId").value(2L));
    }
}
