package com.example.chapter4_umc.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "SUCCESS200_1",
            "요청이 성공적으로 처리되었습니다."),

    CREATED(HttpStatus.CREATED,
            "SUCCESS201_1",
            "리소스가 성공적으로 생성되었습니다."),

    UPDATED(HttpStatus.OK,
            "SUCCESS200_2",
            "리소스가 성공적으로 수정되었습니다."),

    DELETED(HttpStatus.OK,
            "SUCCESS200_3",
            "리소스가 성공적으로 삭제되었습니다."),

    REVIEW_READ(HttpStatus.OK,
            "REVIEW200_1",
            "리뷰 조회 성공"),

    REVIEW_CREATED(HttpStatus.CREATED,
            "REVIEW201_1",
            "리뷰가 성공적으로 생성되었습니다."),

    REVIEW_UPDATED(HttpStatus.OK,
            "REVIEW200_2",
            "리뷰 수정 성공"),

    REVIEW_DELETED(HttpStatus.OK,
            "REVIEW200_3",
            "리뷰 삭제 성공"),

    MISSION_JOIN(HttpStatus.OK,
            "MISSION200_1",
            "미션 도전 성공")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
