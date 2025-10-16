package com.example.umc.domain.member.entity;

import com.example.umc.domain.baseaddress.entity.BaseAddress;
import com.example.umc.domain.food.entity.MemberFavoriteFood;
import com.example.umc.domain.member.enums.Gender;
import com.example.umc.domain.member.enums.SocialType;
import com.example.umc.domain.mission.entity.MemberMission;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.domain.term.entity.MemberTerm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE member_id = ?")
@Where(clause = "deleted_at IS NULL")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "social_uid")
    private String socialUid;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "member_name", nullable = false, length = 100)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "member_point", nullable = false)
    private Integer memberPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_address_id", nullable = false)
    private BaseAddress baseAddress;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberFavoriteFood> memberFavoriteFoods = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberMission> memberMissions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTerm> memberTerms = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
