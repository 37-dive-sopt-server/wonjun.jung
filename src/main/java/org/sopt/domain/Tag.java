package org.sopt.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Tag {
    CS,
    DB,
    SPRING,
    ETC;

    @JsonCreator
    public static Tag from(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("태그 값은 null이거나 비어있을 수 없습니다.");
        }

        String trimmedInput = input.trim();

        return Arrays.stream(values())
                .filter(tag -> tag.name().equals(trimmedInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "유효하지 않은 태그 값입니다." + input +  "(CS | DB | SPRING | ETC 중 입력 가능합니다.)"
                ));
    }
}
