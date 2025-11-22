package com.example.umc.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "COMMON200",
            "성공했습니다."),
    CREATED(HttpStatus.CREATED,
            "COMMON201",
            "요청 성공 및 리소스 생성됨");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
