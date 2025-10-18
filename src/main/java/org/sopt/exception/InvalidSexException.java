package org.sopt.exception;

public class InvalidSexException extends ValidationException {
    public InvalidSexException() {
        super("유효한 성별을 선택해주세요.(남성:0, 여성:1)");
    }
}
