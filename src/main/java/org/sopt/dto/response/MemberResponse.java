package org.sopt.dto.response;

import jakarta.validation.constraints.Email;
import org.sopt.domain.Sex;

import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String name,
        LocalDate birthDate,
        String email,
        Sex sex
) {
}
