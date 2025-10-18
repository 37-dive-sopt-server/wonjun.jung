package org.sopt.exception;

public class InvalidNumberFormatException extends ValidationException {
    public InvalidNumberFormatException() {
        super("유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
    }

    public InvalidNumberFormatException(Throwable cause) {
        super("유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.", cause);
    }
}
