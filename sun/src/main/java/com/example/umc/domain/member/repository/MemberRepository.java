package com.example.umc.domain.member.repository;

import com.example.umc.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 회원의 지역명 조회
     * @param memberId 회원 ID
     * @return 회원의 지역명 (BaseAddress의 baseAddressName)
     */
    @Query("SELECT m.baseAddress.baseAddressName " +
           "FROM Member m " +
           "WHERE m.id = :memberId")
    String findRegionNameByMemberId(@Param("memberId") Long memberId);
}
