package org.sopt.controller;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.service.MemberServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberController {

    private final MemberServiceImpl memberService = new MemberServiceImpl();

    public Long createMember(String name, LocalDate birthDate, String email, Sex sex) {
        return memberService.join(name, birthDate, email, sex);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public Long deleteMember(Long memberId) {
        return memberService.delete(memberId);
    }
}