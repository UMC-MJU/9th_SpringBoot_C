package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 메서드 이름으로 쿼리 생성
    List<MemberMission> findByMemberMemberId(Long memberId);

    List<MemberMission> findByMissionMissionId(Long missionId);

    Optional<MemberMission> findByMemberMemberIdAndMissionMissionId(Long memberId, Long missionId);

    boolean existsByMemberMemberIdAndMissionMissionId(Long memberId, Long missionId);

    // @Query 사용
    @Query("SELECT mm FROM MemberMission mm JOIN FETCH mm.mission WHERE mm.member.memberId = :memberId")
    List<MemberMission> findByMemberIdWithMission(@Param("memberId") Long memberId);

    @Query("SELECT mm FROM MemberMission mm JOIN FETCH mm.member WHERE mm.mission.missionId = :missionId")
    List<MemberMission> findByMissionIdWithMember(@Param("missionId") Long missionId);

    @Query("SELECT COUNT(mm) FROM MemberMission mm WHERE mm.member.memberId = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(mm) FROM MemberMission mm WHERE mm.mission.missionId = :missionId")
    Long countByMissionId(@Param("missionId") Long missionId);
}
