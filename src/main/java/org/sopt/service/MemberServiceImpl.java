package org.sopt.service;

import org.sopt.common.ErrorCode;
import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.dto.request.MemberUpdateRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.exception.BusinessException;
import org.sopt.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(@Qualifier("fileMemberRepository") MemberRepository memberRepository) {
        // "fileMemberRepository" 또는 "memoryMemberRepository" 선택 가능
        this.memberRepository = memberRepository;
    }
    
    // 회원 추가
    public MemberResponse join(String name, LocalDate birthDate, String email, Sex sex) {
        validateAdult(birthDate);
        validateDuplicateEmail(email);

        Member member = new Member(null, name, birthDate, email, sex);
        memberRepository.save(member);

        return MemberResponse.from(member);
    }
    
    // 회원 조회
    public MemberResponse findOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponse.from(member);
    }
    
    // 전체 회원 조회
    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }
    
    // 회원 삭제
    public Long delete(Long memberId) {
        return memberRepository.delete(memberId);
    }

    // 회원 업데이트
    @Override
    public MemberResponse update(Long memberId, MemberUpdateRequest request) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!existingMember.getEmail().equals(request.email())) {
            validateDuplicateEmail(request.email());
        }

        validateAdult(request.birthDate());

        Member updatedMember = new Member(
                memberId,
                request.name(),
                request.birthDate(),
                request.email(),
                request.sex()
        );

        memberRepository.update(updatedMember);

        return MemberResponse.from(updatedMember);
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
