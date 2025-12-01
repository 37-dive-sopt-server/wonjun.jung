package org.sopt.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.ErrorCode;
import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.dto.request.MemberUpdateRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.exception.BusinessException;
import org.sopt.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    
    // 회원 추가
    @Transactional
    public MemberResponse join(String name, LocalDate birthDate, String email, Sex sex) {
        validateAdult(birthDate);
        validateDuplicateEmail(email);

        Member member = Member.of(name, birthDate, email, sex);
        Member savedMember = memberRepository.save(member);

        return MemberResponse.from(savedMember);
    }
    
    // 회원 조회
    @Transactional(readOnly = true)
    public MemberResponse findOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponse.from(member);
    }
    
    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }
    
    // 회원 삭제
    @Transactional
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // 회원 업데이트
    @Transactional
    public MemberResponse update(Long memberId, MemberUpdateRequest req) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.getEmail().equals(req.email())) {
            validateDuplicateEmail(req.email());
        }

        validateAdult(req.birthDate());

        member.update(req.name(), req.birthDate(), req.email(), req.sex());

        return MemberResponse.from(member);
    }
    
    // 이메일 중복체크
    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
                });
    }
    
    // 성인 인증
    private void validateAdult(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears() + 1;
        if (age < 20) {
            throw new BusinessException(ErrorCode.UNDERAGE);
        }
    }
}
