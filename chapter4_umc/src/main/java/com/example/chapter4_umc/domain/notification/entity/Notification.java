package com.example.chapter4_umc.domain.notification.entity;

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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    // N:1 관계: Notification (N) : Member (1)
    // 한 명의 사용자에게 여러 알림 발송
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 100) // 알림 유형 (VARCHAR 100)
    private String type;

    @Column(columnDefinition = "TEXT") // 알림 내용 (TEXT)
    private String content;

    private Boolean isRead; // 읽음 여부 (BOOLEAN)

    @CreationTimestamp // 발송 일시 (TIMESTAMP)
    private LocalDateTime sentAt;
}