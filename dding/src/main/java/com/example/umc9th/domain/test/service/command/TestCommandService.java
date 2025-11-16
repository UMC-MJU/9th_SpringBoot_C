package com.example.umc9th.domain.test.service.command;

import com.example.umc9th.domain.test.dto.req.TestReqDTO;
import com.example.umc9th.domain.test.entity.TestEntity;

public interface TestCommandService {
    TestEntity createTestEntity(TestReqDTO.Testing request);
}
