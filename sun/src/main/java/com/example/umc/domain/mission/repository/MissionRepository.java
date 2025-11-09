package com.example.umc.domain.mission.repository;

import com.example.umc.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    /**
     * 도전 가능한 미션 목록 조회 (회원이 아직 도전하지 않은 미션)
     * - 회원의 지역(BaseAddress)에 있는 미션만 조회
     * - 이미 도전한 미션(MemberMission에 존재)은 제외
     * - deadline 오름차순 정렬 (마감일 임박순)
     * - JOIN FETCH로 N+1 문제 해결
     *
     * @param memberId 회원 ID
     * @param baseAddressId 회원의 지역 ID
     * @param pageable 페이징 정보
     * @return 도전 가능한 미션 목록 (페이징)
     */
    @Query(value = "SELECT m FROM Mission m " +
                   "JOIN FETCH m.restaurant r " +
                   "JOIN FETCH r.baseAddress ba " +
                   "WHERE ba.id = :baseAddressId " +
                   "AND m.id NOT IN (" +
                   "    SELECT mm.mission.id FROM MemberMission mm " +
                   "    WHERE mm.member.id = :memberId" +
                   ") " +
                   "ORDER BY m.deadline ASC",
           countQuery = "SELECT COUNT(m) FROM Mission m " +
                        "JOIN m.restaurant r " +
                        "WHERE r.baseAddress.id = :baseAddressId " +
                        "AND m.id NOT IN (" +
                        "    SELECT mm.mission.id FROM MemberMission mm " +
                        "    WHERE mm.member.id = :memberId" +
                        ")")
    Page<Mission> findAvailableMissionsByRegion(
            @Param("memberId") Long memberId,
            @Param("baseAddressId") Long baseAddressId,
            Pageable pageable
    );
}
