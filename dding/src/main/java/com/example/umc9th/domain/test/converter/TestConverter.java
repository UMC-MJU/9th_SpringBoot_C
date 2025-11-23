package com.example.umc9th.domain.test.converter;

import com.example.umc9th.domain.test.dto.req.TestReqDTO;
import com.example.umc9th.domain.test.dto.res.TestResDTO;
import com.example.umc9th.domain.test.entity.TestEntity;

public class TestConverter {

    public static TestResDTO.Testing toTestingDTO(
            String testing
    ) {
        return TestResDTO.Testing.builder()
                .testString(testing)
                .build();
    }

    public static TestResDTO.Exception toExceptionDTO(
            String testing
    ){
        return TestResDTO.Exception.builder()
                .testString(testing)
                .build();
    }

    public static TestEntity toTestEntity(TestReqDTO.Testing request) {
        return TestEntity.builder()
                .text(request.getTestString())
                .build();
    }

    public static TestResDTO.CreateTestEntityResultDTO toCreateTestEntityResultDTO(TestEntity entity) {
        return TestResDTO.CreateTestEntityResultDTO.builder()
                .id(entity.getId())
                .text(entity.getText())
                .build();
    }
}
