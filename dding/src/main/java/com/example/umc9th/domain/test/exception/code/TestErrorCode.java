package com.example.umc9th.domain.test.exception.code;

import com.example.umc9th.global.apiPayload.ReasonDTO;
import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements BaseErrorCode {

    // For test
    TEST_ERROR_CODE(HttpStatus.BAD_REQUEST, "TEST400_1", "이거는 테스트"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
