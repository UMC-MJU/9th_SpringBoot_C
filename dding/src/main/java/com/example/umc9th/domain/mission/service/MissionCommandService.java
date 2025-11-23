package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.dto.MissionRequestDTO;
import com.example.umc9th.domain.mission.entity.Mission;

public interface MissionCommandService {
    Mission createMission(MissionRequestDTO.CreateDTO request);
}
