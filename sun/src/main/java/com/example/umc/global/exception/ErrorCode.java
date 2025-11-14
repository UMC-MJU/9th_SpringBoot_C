package com.example.umc.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "회원을 찾을 수 없습니다."),

    // Restaurant 관련 에러
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "RESTAURANT-001", "레스토랑을 찾을 수 없습니다."),

    // Review 관련 에러
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW-001", "리뷰를 찾을 수 없습니다."),

    // 입력값 검증 에러
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 입력값입니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "COMMON-002", "페이지 크기는 1에서 100 사이여야 합니다."),
    INVALID_STAR_RATING(HttpStatus.BAD_REQUEST, "COMMON-003", "별점은 0.0에서 5.0 사이여야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
