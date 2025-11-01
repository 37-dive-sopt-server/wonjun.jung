package org.sopt.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.domain.Sex;

import java.time.LocalDate;

public record MemberCreateRequest(
        @NotBlank(message = "이름을 입력해주세요")
        String name,

        @NotNull(message = "생년월일을 입력해주세요")
        LocalDate birthDate,

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        String email,

        @NotNull(message = "성별을 입력해주세요")
        Sex sex
) {}