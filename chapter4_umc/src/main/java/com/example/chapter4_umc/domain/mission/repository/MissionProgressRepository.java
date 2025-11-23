package com.example.chapter4_umc.domain.mission.repository;

import com.example.chapter4_umc.domain.mission.entity.MissionProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionProgressRepository extends JpaRepository<MissionProgress, Long> {
}
