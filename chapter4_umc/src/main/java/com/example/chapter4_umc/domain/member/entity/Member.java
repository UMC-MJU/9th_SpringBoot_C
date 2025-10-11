package com.example.chapter4_umc.domain.member.entity;

import com.example.chapter4_umc.domain.member.entity.Region;
import com.example.chapter4_umc.domain.member.enums.Gender;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import javax.management.Notification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "member")
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50) // 닉네임 (VARCHAR 50)
    private String nickname;

    @Column(length = 100) // 비밀번호 (VARCHAR 100)
    private String password;

    @Column(length = 100, unique = true) // 이메일 (VARCHAR 100)
    private String email;

    @Column(length = 20) // 전화번호 (VARCHAR 20)
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 300) // 주소 (VARCHAR 300)
    private String address;

    private LocalDate birthDate; // 생년월일 (DATE)

    @CreationTimestamp // 가입일시 (TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp // 수정일시 (TIMESTAMP)
    private LocalDateTime updatedAt;

    @Builder.Default // Builder 사용 시 기본값 설정
    @Column(columnDefinition = "INT default 0") // DDL 기본값 유지
    private Integer totalPoints = 0;

    @ColumnDefault("1")
    private Integer isActive;

    private Boolean eventAlarm;
    private Boolean reviewAlarm;
    private Boolean inquiryAlarm;
    private Boolean phoneVerified;

    // N:1 관계: Member (N) : Region (1)
    // 여러 사용자가 하나의 지역을 선호 지역으로 설정 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preferred_region_id")
    private Region preferredRegion;

    // 1:N 관계: Member (1) : MemberMission (N)
    // 사용자 한 명이 여러 개의 미션 수행 가능
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberMission> MemberMissionList;

    // 1:N 관계: Member (1) : Inquiry (N)
    // 한 명의 사용자가 여러 문의를 남길 수 있음
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList;

    // 1:N 관계: Member (1) : Notification (N)
    // 한 명의 사용자에게 여러 알림 발송
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notificationList;

    // 1:N 관계: Member (1) : Review (N)
    // 사용자는 리뷰를 여러 개 등록할 수 있음
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    // N:M 관계: Member (N) : FoodPreference (M)
    // 한 명의 사용자가 여러 선호 음식 설정 가능
    // MemberPreference 테이블을 통해 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPreference> memberPreferenceList;
}