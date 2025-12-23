package com.example.umc9th.global.apiPayload.handler;

import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * 우리가 직접 정의한 GeneralException에 대한 처리
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException e) {
        BaseErrorCode code = e.getCode();
        return handleExceptionInternal(e, code);
    }

    /**
     * @CheckPage 와 같이, 컨트롤러에서 유효성 검증에 실패했을 때 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException without message"));

        // Enum으로 변환하여 처리
        GeneralErrorCode generalErrorCode = GeneralErrorCode.valueOf(errorMessage);
        return handleExceptionInternal(e, generalErrorCode);
    }

    /**
     * @RequestBody 에서 유효성 검증에 실패했을 때 처리
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
            errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
        });

        return handleExceptionInternal(ex, GeneralErrorCode.BAD_REQUEST, errors);
    }

    /**
     * 위에서 지정한 예외 외에, 서버 내부에서 발생하는 모든 예외를 처리
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleAllOtherExceptions(Exception e, WebRequest request) {
        log.error("Unhandled exception occurred: ", e);
        return handleExceptionInternal(e, GeneralErrorCode.INTERNAL_SERVER_ERROR);
    }


    // 내부적으로 사용될 응답 생성 메소드들
    private ResponseEntity<Object> handleExceptionInternal(Exception e, BaseErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, BaseErrorCode errorCode, Map<String, String> errorArgs) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode, errorArgs));
    }
}
