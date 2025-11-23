package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.mission.dto.MissionRequestDTO;
import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.store.entity.Store;
import java.time.LocalDateTime;

public class MissionConverter {

    public static MissionResponseDTO.CreateResultDTO toCreateResultDTO(Mission mission){
        return MissionResponseDTO.CreateResultDTO.builder()
                .missionId(mission.getMissionId())
                .createdAt(mission.getCreateAt())
                .build();
    }

    public static Mission toMission(MissionRequestDTO.CreateDTO request, Store store){
        return Mission.builder()
                .deadline(request.getDeadline())
                .conditional(request.getConditional())
                .point(request.getPoint())
                .store(store)
                .createAt(LocalDateTime.now())
                .build();
    }
}
