package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;

    @Override
    public Page<Mission> getMissionList(Long storeId, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return missionRepository.findAllByStoreId(storeId, pageRequest);
    }
}
