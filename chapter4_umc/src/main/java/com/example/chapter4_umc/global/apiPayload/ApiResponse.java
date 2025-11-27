package com.example.chapter4_umc.global.apiPayload;

import com.example.chapter4_umc.global.apiPayload.code.BaseErrorCode;
import com.example.chapter4_umc.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private final T result;

    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(
                true,
                code.getCode(),
                code.getMessage(),
                result
        );
    }

    public static ApiResponse<?> onSuccess(BaseSuccessCode code){
        return new ApiResponse<>(
                true,
                code.getCode(),
                code.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result){
        return new ApiResponse<>(
                false,
                code.getCode(),
                code.getMessage(),
                result
        );
    }

    public static ApiResponse<?> onFailure(BaseErrorCode code) {
        return new ApiResponse<>(
                false,
                code.getCode(),
                code.getMessage(),
                null
        );
    }
}
