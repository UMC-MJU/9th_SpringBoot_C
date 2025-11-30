package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    Page<MemberMission> findAllByMemberId(Long memberId, Pageable pageable);
}
