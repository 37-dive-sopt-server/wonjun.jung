package org.sopt.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Arrays;

public enum Sex implements Serializable {
    MALE,
    FEMALE;

    @JsonCreator
    public static Sex from(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("성별 값은 null이거나 비어있을 수 없습니다.");
        }

        String trimmedInput = input.trim();

        return Arrays.stream(values())
                .filter(sex -> sex.name().equals(trimmedInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "유효하지 않은 성별 값입니다: " + input + " (MALE 또는 FEMALE만 입력 가능합니다)"
                ));
    }
}