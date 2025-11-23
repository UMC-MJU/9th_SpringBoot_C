package com.example.umc9th.domain.test.repository;

import com.example.umc9th.domain.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
