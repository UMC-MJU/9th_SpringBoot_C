package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.member.enums.SocialType;
import com.example.umc9th.domain.member.enums.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 메서드 이름으로 쿼리 생성
    Optional<Member> findByEmail(String email);

    List<Member> findByGender(Gender gender);

    List<Member> findByNameContaining(String keyword);

    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);

    boolean existsByEmail(String email);

    List<Member> findByAddress(Address address);

    // @Query 사용
    @Query("SELECT m FROM Member m WHERE m.point >= :minPoint ORDER BY m.point DESC")
    List<Member> findMembersWithMinPoint(@Param("minPoint") Integer minPoint);

    @Query("SELECT m FROM Member m WHERE m.birth BETWEEN :start AND :end")
    List<Member> findByBirthBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT m FROM Member m JOIN FETCH m.memberFoodList WHERE m.memberId = :memberId")
    Optional<Member> findByIdWithFoods(@Param("memberId") Long memberId);

    @Query("SELECT m FROM Member m JOIN FETCH m.reviewList WHERE m.memberId = :memberId")
    Optional<Member> findByIdWithReviews(@Param("memberId") Long memberId);

    @Query("SELECT m FROM Member m WHERE m.deletedAt IS NULL")
    List<Member> findActiveMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.address = :address")
    Long countByAddress(@Param("address") Address address);
}
