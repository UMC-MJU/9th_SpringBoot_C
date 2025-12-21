package com.example.umc.domain.member.service;

import com.example.umc.domain.baseaddress.entity.BaseAddress;
import com.example.umc.domain.baseaddress.repository.BaseAddressRepository;
import com.example.umc.domain.food.entity.Food;
import com.example.umc.domain.food.entity.MemberFavoriteFood;
import com.example.umc.domain.food.repository.FoodRepository;
import com.example.umc.domain.member.converter.MemberConverter;
import com.example.umc.domain.member.dto.request.MemberRequestDto;
import com.example.umc.domain.member.dto.response.MemberResponseDto;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.global.auth.Role;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import com.example.umc.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BaseAddressRepository baseAddressRepository;
    private final FoodRepository foodRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     * @param dto 로그인 요청 DTO
     * @return 로그인 응답 DTO (JWT 토큰 포함)
     */
    public MemberResponseDto.LoginDto login(MemberRequestDto.LoginDto dto) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 3. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail());

        // 4. 응답 DTO 생성
        return MemberResponseDto.LoginDto.builder()
                .accessToken(accessToken)
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .build();
    }

    /**
     * 회원가입
     * @param dto 회원가입 요청 DTO
     * @return 회원가입 응답 DTO
     */
    @Transactional
    public MemberResponseDto.JoinDto signup(MemberRequestDto.JoinDto dto) {
        // 1. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 2. BaseAddress 조회
        BaseAddress baseAddress = baseAddressRepository.findByBaseAddressName(dto.getAddress())
                .orElseThrow(() -> new CustomException(ErrorCode.BASE_ADDRESS_NOT_FOUND));

        // 3. Member 엔티티 생성 (Converter 사용)
        Member member = MemberConverter.toMember(dto, encodedPassword, Role.ROLE_USER, baseAddress);

        // 4. Member 저장
        Member savedMember = memberRepository.save(member);

        // 5. 선호 음식 카테고리 처리
        if (dto.getPreferCategory() != null && !dto.getPreferCategory().isEmpty()) {
            List<MemberFavoriteFood> favoriteFoods = dto.getPreferCategory().stream()
                    .map(foodId -> {
                        Food food = foodRepository.findById(foodId)
                                .orElseThrow(() -> new CustomException(ErrorCode.FOOD_NOT_FOUND));
                        return MemberFavoriteFood.builder()
                                .member(savedMember)
                                .food(food)
                                .build();
                    })
                    .collect(Collectors.toList());

            // 양방향 관계 설정
            savedMember.getMemberFavoriteFoods().addAll(favoriteFoods);
        }

        // 6. Entity -> DTO 변환 후 반환
        return MemberResponseDto.JoinDto.from(savedMember);
    }

    /**
     * 마이페이지 조회 (메서드 이름으로 쿼리 생성 - findById 사용)
     * @param memberId JWT 토큰에서 추출한 회원 ID
     * @return 마이페이지 정보 (닉네임, 이메일, 전화번호, 인증여부, 포인트)
     */
    public MemberResponseDto.MyPageDto getMyPage(Long memberId) {
        // 1. 회원 조회 (JpaRepository의 findById 메서드 사용)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. Entity -> DTO 변환
        return MemberResponseDto.MyPageDto.from(member);
    }
}
