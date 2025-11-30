package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

public interface MissionQueryService {
    Page<Mission> getMissionList(Long storeId, Integer page);
}
