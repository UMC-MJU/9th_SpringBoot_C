package com.example.umc9th.domain.test.service.query;

import com.example.umc9th.domain.test.exception.TestException;
import com.example.umc9th.domain.test.exception.code.TestErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestQueryServiceImpl implements TestQueryService {

    @Override
    public void checkFlag(Long flag) {
        if (flag == 1) {
            throw new TestException(TestErrorCode.TEST_ERROR_CODE);
        }
    }
}
