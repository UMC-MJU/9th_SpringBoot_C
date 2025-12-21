package com.example.umc.global.exception;

import com.example.umc.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    // Member 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "회원을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER-002", "비밀번호가 일치하지 않습니다."),

    // BaseAddress 관련 에러
    BASE_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS-001", "주소를 찾을 수 없습니다."),

    // Food 관련 에러
    FOOD_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD-001", "음식 카테고리를 찾을 수 없습니다."),

    // Restaurant 관련 에러
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "RESTAURANT-001", "레스토랑을 찾을 수 없습니다."),

    // Review 관련 에러
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW-001", "리뷰를 찾을 수 없습니다."),

    // Mission 관련 에러
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION-001", "미션을 찾을 수 없습니다."),
    ALREADY_CHALLENGING_MISSION(HttpStatus.CONFLICT, "MISSION-002", "이미 도전 중인 미션입니다."),
    MEMBER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION-003", "회원의 미션을 찾을 수 없습니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.CONFLICT, "MISSION-004", "이미 완료된 미션입니다."),

    // 입력값 검증 에러
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 입력값입니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "COMMON-002", "페이지 크기는 1에서 100 사이여야 합니다."),
    INVALID_STAR_RATING(HttpStatus.BAD_REQUEST, "COMMON-003", "별점은 0.0에서 5.0 사이여야 합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
