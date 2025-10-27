package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.dto.response.MemberResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    MemberResponse join(String name, LocalDate birthday, String email, Sex sex);

    Optional<Member> findOne(Long memberId);

    List<Member> findAllMembers();

    Long delete(Long memberId);

    /**
     * 이메일 중복 검증 (실시간 검증용)
     * @param email 검증할 이메일
     * @throws org.sopt.exception.DuplicateEmailException 이메일이 중복된 경우
     */
    void checkEmailDuplicate(String email);
}
