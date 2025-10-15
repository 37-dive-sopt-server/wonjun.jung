package org.sopt.exception;

public class InvalidNameException extends ValidationException {
    public InvalidNameException() {
        super("이름을 입력해주세요.");
    }
}