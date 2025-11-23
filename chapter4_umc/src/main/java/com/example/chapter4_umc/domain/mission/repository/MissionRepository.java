package com.example.chapter4_umc.domain.mission.repository;

import com.example.chapter4_umc.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
