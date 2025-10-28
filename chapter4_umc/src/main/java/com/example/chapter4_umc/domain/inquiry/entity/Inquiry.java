package com.example.chapter4_umc.domain.inquiry.entity;

import com.example.chapter4_umc.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    // N:1 관계: Inquiry (N) : Member (1)
    // 한 명의 사용자가 여러 문의를 남길 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50) // 문의 유형 (VARCHAR 50)
    private String type;

    @Column(length = 100) // 제목 (VARCHAR 100)
    private String title;

    @Column(columnDefinition = "TEXT") // 내용 (TEXT)
    private String content;

    @Column(length = 20) // 처리 상태 (VARCHAR 20)
    private String status;

    @Column(length = 30) // 첨부 이미지 (VARCHAR 30)
    private String attachedImage;

    @CreationTimestamp // 작성일 (TIMESTAMP)
    private LocalDateTime createdAt;
}