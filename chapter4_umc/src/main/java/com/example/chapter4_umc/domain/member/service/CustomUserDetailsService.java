package com.example.chapter4_umc.domain.member.service;

import com.example.chapter4_umc.domain.member.entity.Member;
import com.example.chapter4_umc.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자"));

        return org.springframework.security.core.userdetails.User
                .withUsername(member.getEmail())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}
