package org.sopt.exception;

public class InvalidIdException extends ValidationException {
    public InvalidIdException() {
        super("유효하지 않은 ID입니다.");
    }
}