package com.example.chapter4_umc.domain.member.repository;

import com.example.chapter4_umc.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
