package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 메서드 이름으로 쿼리 생성
    List<Mission> findByStoreStoreId(Long storeId);

    List<Mission> findByPointGreaterThanEqual(Integer minPoint);

    List<Mission> findByDeadlineAfter(LocalDate date);

    List<Mission> findByDeadlineBefore(LocalDate date);

    // @Query 사용
    @Query("SELECT m FROM Mission m WHERE m.store.storeId = :storeId AND m.deadline >= :today ORDER BY m.deadline ASC")
    List<Mission> findActiveByStore(@Param("storeId") Long storeId, @Param("today") LocalDate today);

    @Query("SELECT m FROM Mission m WHERE m.deadline BETWEEN :start AND :end")
    List<Mission> findByDeadlineBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT m FROM Mission m JOIN FETCH m.store WHERE m.missionId = :missionId")
    Optional<Mission> findByIdWithStore(@Param("missionId") Long missionId);

    @Query("SELECT m FROM Mission m WHERE m.store.storeId = :storeId ORDER BY m.point DESC")
    List<Mission> findByStoreOrderByPointDesc(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(m) FROM Mission m WHERE m.store.storeId = :storeId")
    Long countByStoreId(@Param("storeId") Long storeId);
}
