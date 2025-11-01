package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.enums.FoodName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {

    // 메서드 이름으로 쿼리 생성
    List<MemberFood> findByMemberMemberId(Long memberId);

    List<MemberFood> findByFoodName(FoodName foodName);

    boolean existsByMemberMemberIdAndFoodName(Long memberId, FoodName foodName);

    // @Query 사용
    @Query("SELECT mf FROM MemberFood mf JOIN FETCH mf.member WHERE mf.food.name = :foodName")
    List<MemberFood> findByFoodNameWithMember(@Param("foodName") FoodName foodName);

    @Query("SELECT mf FROM MemberFood mf JOIN FETCH mf.food WHERE mf.member.memberId = :memberId")
    List<MemberFood> findByMemberIdWithFood(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(mf) FROM MemberFood mf WHERE mf.member.memberId = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);
}
