package org.sopt.controller;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.service.MemberService;
import org.sopt.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/users")
    public Long createMember(@RequestParam String name, @RequestParam LocalDate birthDate, @RequestParam String email, @RequestParam Sex sex) {
        MemberValidator.validateName(name);
        MemberValidator.validateEmail(email);
        return memberService.join(name, birthDate, email, sex);
    }

    @GetMapping("/users")
    public Optional<Member> findMemberById(@RequestParam Long id) {
        MemberValidator.validateId(id);
        return memberService.findOne(id);
    }

    @GetMapping("/usersAll")
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