package org.sopt.exception;

public class UnderageException extends BusinessException {
    public UnderageException(int age) {
        super("20세 미만의 회원은 가입할 수 없습니다. (현재 나이: " + age + "세)");
    }
}