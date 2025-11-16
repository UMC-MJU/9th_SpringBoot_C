package com.example.umc9th.domain.test.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestResDTO {

    @Builder
    @Getter
    public static class Testing {
        private String testString;
    }

    @Builder
    @Getter
    public static class Exception {
        private String testString;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateTestEntityResultDTO {
        private Long id;
        private String text;
    }
}
