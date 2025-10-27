package org.sopt.validator;

import org.sopt.domain.Sex;
import org.sopt.exception.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MemberValidator {
    
    // 이름 검증
    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException();
        }
    }
    
    // 이메일 형식 검증
    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidEmailException();
        }
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(regex)) {
            throw new InvalidEmailFormatException();
        }
    }
    
    // 날짜 검증
    public static LocalDate validateAndConvertDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(e);
        }
    }

    public static Long validateAndConvertId(String numberString) {
        Long id;
        try {
            id = Long.parseLong(numberString);
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormatException(e);
        }

        if (id <= 0) {
            throw new InvalidIdException();
        }

        return id;
    }

    public static void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException();
        }
    }

    public static Sex validateAndConvertSex(String sexChoice) {
        if ("0".equals(sexChoice)) {
            return Sex.MALE;
        } else if ("1".equals(sexChoice)) {
            return Sex.FEMALE;
        }
        throw new InvalidSexException();
    }
}
