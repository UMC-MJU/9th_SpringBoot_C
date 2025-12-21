package com.example.umc.domain.mission.repository;

import com.example.umc.domain.mission.entity.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    /**
     * 회원의 미션 목록 조회 (상태별 필터링, 페이징)
     * @Query 방식: JOIN FETCH로 N+1 문제 해결
     * countQuery: COUNT 쿼리 최적화 (불필요한 JOIN 제거)
     */
    @Query(value = "SELECT mm FROM MemberMission mm " +
                   "JOIN FETCH mm.mission m " +
                   "JOIN FETCH m.restaurant r " +
                   "WHERE mm.member.id = :memberId " +
                   "AND mm.isComplete = :isComplete " +
                   "ORDER BY mm.createdAt DESC",
           countQuery = "SELECT COUNT(mm) FROM MemberMission mm " +
                        "WHERE mm.member.id = :memberId " +
                        "AND mm.isComplete = :isComplete")
    Page<MemberMission> findMissionsByMemberAndStatus(
            @Param("memberId") Long memberId,
            @Param("isComplete") Boolean isComplete,
            Pageable pageable
    );

    /**
     * 회원이 완료한 미션 개수 조회
     * @param memberId 회원 ID
     * @return 완료한 미션 개수
     */
    @Query("SELECT COUNT(mm) " +
           "FROM MemberMission mm " +
           "WHERE mm.member.id = :memberId " +
           "AND mm.isComplete = true")
    Long countCompletedMissions(@Param("memberId") Long memberId);

    /**
     * 회원이 특정 미션을 이미 도전 중인지 확인
     * @param memberId 회원 ID
     * @param missionId 미션 ID
     * @return 도전 중이면 true, 아니면 false
     */
    boolean existsByMemberIdAndMissionId(Long memberId, Long missionId);

    /**
     * 회원 ID와 미션 ID로 MemberMission 조회
     * @param memberId 회원 ID
     * @param missionId 미션 ID
     * @return MemberMission
     */
    @Query("SELECT mm FROM MemberMission mm " +
           "JOIN FETCH mm.mission m " +
           "WHERE mm.member.id = :memberId " +
           "AND mm.mission.id = :missionId")
    java.util.Optional<MemberMission> findByMemberIdAndMissionId(
            @Param("memberId") Long memberId,
            @Param("missionId") Long missionId
    );
}
