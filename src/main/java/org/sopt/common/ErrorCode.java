package org.sopt.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 회원 관련 예외
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "요청값이 유효하지 않습니다."),
    UNDERAGE(HttpStatus.BAD_REQUEST, "미성년자는 가입할 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // 아티클 관련 예외
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "아티클을 찾을 수 없습니다."),

    // 댓글 관련 예외
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }
}

