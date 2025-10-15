package org.sopt.exception;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(String email) {
        super("이미 존재하는 이메일입니다: " + email);
    }
}