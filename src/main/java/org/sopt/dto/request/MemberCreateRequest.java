package org.sopt.dto.request;

import org.sopt.domain.Sex;

import java.time.LocalDate;

public record MemberCreateRequest(
        String name,
        LocalDate birthDate,
        String email,
        Sex sex
) {}