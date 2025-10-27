package org.sopt.controller;

import org.sopt.domain.Member;
import org.sopt.dto.request.MemberCreateRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.service.MemberService;
import org.sopt.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    // 회원 추가
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberCreateRequest req) {
        MemberValidator.validateName(req.name());
        MemberValidator.validateEmail(req.email());
        MemberResponse res = memberService.join(req.name(), req.birthDate(), req.email(), req.sex());
        return ResponseEntity.ok(res);
    }
    
    // 회원 조회
    @GetMapping
    public Optional<Member> findMemberById(@RequestParam Long id) {
        MemberValidator.validateId(id);
        return memberService.findOne(id);
    }
    
    // 전체 회원 조회
    @GetMapping("/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    // 회원 삭제
    @DeleteMapping
    public Long deleteMember(Long memberId) {
        MemberValidator.validateId(memberId);
        return memberService.delete(memberId);
    }
    
    // 회원 정보 업데이트
    @PutMapping
    public void updateMember() {
    }


}