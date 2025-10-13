package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    public Long join(String name, LocalDate birthday, String email, Sex sex);

    public Optional<Member> findOne(Long memberId);

    public List<Member> findAllMembers();

    private void validateDuplicateEmail(String email) {

    }

    public Long delete(Long memberId);
}
