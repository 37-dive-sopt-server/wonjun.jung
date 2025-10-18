package org.sopt.controller;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.service.MemberService;
import org.sopt.validator.MemberValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public Long createMember(String name, LocalDate birthDate, String email, Sex sex) {
        MemberValidator.validateName(name);
        MemberValidator.validateEmail(email);
        return memberService.join(name, birthDate, email, sex);
    }

    public Optional<Member> findMemberById(Long id) {
        MemberValidator.validateId(id);
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public Long deleteMember(Long memberId) {
        MemberValidator.validateId(memberId);
        return memberService.delete(memberId);
    }

    public void checkEmailDuplicate(String email) {
        MemberValidator.validateEmail(email);
        memberService.checkEmailDuplicate(email);
    }
}