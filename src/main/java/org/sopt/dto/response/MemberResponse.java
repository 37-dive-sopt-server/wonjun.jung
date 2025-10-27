package org.sopt.dto.response;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;

import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String name,
        LocalDate birthDate,
        String email,
        Sex sex
) {
    // 정적 팩토리 메서드
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getBirthDate(),
                member.getEmail(),
                member.getSex()
        );
    }
}
