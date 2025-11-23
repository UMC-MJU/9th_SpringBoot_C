package com.example.umc9th.domain.test.service.command;

import com.example.umc9th.domain.test.converter.TestConverter;
import com.example.umc9th.domain.test.dto.req.TestReqDTO;
import com.example.umc9th.domain.test.entity.TestEntity;
import com.example.umc9th.domain.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCommandServiceImpl implements TestCommandService {

    private final TestRepository testRepository;

    @Override
    public TestEntity createTestEntity(TestReqDTO.Testing request) {
        TestEntity newEntity = TestConverter.toTestEntity(request);
        return testRepository.save(newEntity);
    }
}
