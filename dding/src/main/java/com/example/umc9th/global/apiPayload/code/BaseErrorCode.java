package com.example.umc9th.global.apiPayload.code;

import com.example.umc9th.global.apiPayload.ReasonDTO;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode extends BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();

    HttpStatus getHttpStatus();
}
