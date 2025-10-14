package com.example.umc9th.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 1️⃣
@EntityListeners(AuditingEntityListener.class) // 2️⃣
@Getter
public abstract class BaseEntity { // 3️⃣

    @CreatedDate // 4️⃣
    @Column(name = "created_at", nullable = false) // updatable = false 추가 권장
    private LocalDateTime createdAt;

    @LastModifiedDate // 5️⃣
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}