package com.example.umc9th.domain.test.dto.req;

import lombok.Getter;

public class TestReqDTO {

    @Getter
    public static class Testing {
        private String testString;
    }

    @Getter
    public static class Exception {
        private String testString;
    }
}
