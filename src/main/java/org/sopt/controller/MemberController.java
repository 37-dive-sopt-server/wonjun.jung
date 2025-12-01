package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.common.ApiResponse;
import org.sopt.dto.request.MemberCreateRequest;
import org.sopt.dto.request.MemberUpdateRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 생성
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@Valid @RequestBody MemberCreateRequest req) {
        MemberResponse res = memberService.join(req.name(), req.birthDate(), req.email(), req.sex());
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> members = memberService.findAllMembers();
        return ResponseEntity.ok(ApiResponse.success(members));
    }
    
    // 단일 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponse>> findMemberById(@PathVariable Long id) {
        MemberResponse res = memberService.findOne(id);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    // 회원 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 회원정보 수정
    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMember(
            @PathVariable Long memberId,
            @Valid @RequestBody MemberUpdateRequest req) {
        MemberResponse res = memberService.update(memberId, req);
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}