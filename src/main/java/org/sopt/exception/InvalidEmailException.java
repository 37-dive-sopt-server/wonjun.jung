package org.sopt.exception;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException() {
        super("이메일을 입력해주세요.");
    }
}
