package org.sopt.exception;

public class InvalidDateFormatException extends ValidationException {
    public InvalidDateFormatException() {
        super("양식에 맞는 생년월일을 입력해주세요.(ex.2002-03-14)");
    }

    public InvalidDateFormatException(Throwable cause) {
        super("양식에 맞는 생년월일을 입력해주세요.(ex.2002-03-14)", cause);
    }
}
