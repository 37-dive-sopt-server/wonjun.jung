package org.sopt.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.sopt.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ===================== 도메인/비즈니스 예외 ===================== */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("[BusinessException] code={}, message={}", errorCode, errorCode.message());
        return ResponseEntity
                .status(errorCode.status())
                .body(ApiResponse.error(errorCode));
    }

    /* ===================== 검증/요청 형식 관련 400 ===================== */

    // @Valid 바디 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        log.warn("[400][MethodArgumentNotValid] {}", errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, errorMessage));
    }

    // JSON 파싱/역직렬화 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Throwable root = e.getMostSpecificCause();
        String msg = "요청 데이터 형식이 올바르지 않습니다.";

        if (root instanceof IllegalArgumentException iae) {
            msg = iae.getMessage();
        } else if (root instanceof InvalidFormatException ife) {
            // 필드 경로
            String fieldPath = ife.getPath() != null
                    ? ife.getPath().stream()
                    .map(ref -> ref.getFieldName())
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("."))
                    : null;

            // enum
            if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
                Object[] allowed = ife.getTargetType().getEnumConstants();
                msg = (fieldPath != null)
                        ? "필드 '" + fieldPath + "' 값이 올바르지 않습니다. 허용값: " + Arrays.toString(allowed)
                        : "요청 값이 올바르지 않습니다. 허용값: " + Arrays.toString(allowed);
            } else {
                msg = (fieldPath != null)
                        ? "필드 '" + fieldPath + "' 값이 올바르지 않습니다."
                        : "요청 데이터 형식이 올바르지 않습니다.";
            }
        }

        log.warn("[400][NotReadable] {}", msg, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, msg));
    }

    // 쿼리/경로 변수 타입 불일치
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("[400][TypeMismatch] param={}, requiredType={}", e.getName(),
                e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "요청 파라미터 형식이 올바르지 않습니다."));
    }

    // 필수 쿼리 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParam(MissingServletRequestParameterException e) {
        log.warn("[400][MissingParam] {} is required", e.getParameterName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "필수 요청 파라미터가 누락되었습니다."));
    }

    /* ===================== 404/405/415 ===================== */

    // 404: 잘못된 URL
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(NoResourceFoundException e) {
        log.warn("[404][NoResourceFound] {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, "요청 URL이 올바르지 않습니다."));
    }

    // 405: 메서드 불일치
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.warn("[405][MethodNotAllowed] supported={}", e.getSupportedHttpMethods());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.error(405, "유효하지 않은 HTTP 메서드입니다."));
    }

    // 415: Content-Type 문제
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        log.warn("[415][MediaTypeNotSupported] contentType={}", e.getContentType());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ApiResponse.error(415, "지원하지 않는 Content-Type 입니다."));
    }

    /* ===================== 500 ===================== */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("[500][Unhandled] {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ErrorCode.INTERNAL_ERROR));
    }
}