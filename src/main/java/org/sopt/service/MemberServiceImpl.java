package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.exception.DuplicateEmailException;
import org.sopt.exception.UnderageException;
import org.sopt.repository.MemberRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    // private final MemoryMemberRepository memberRepository;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(String name, LocalDate birthDate, String email, Sex sex) {
        validateAdult(birthDate);
        validateDuplicateEmail(email);

        Member member = new Member(null, name, birthDate, email, sex);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
            .ifPresent(member -> {
                throw new DuplicateEmailException(email);
            });
    }

    public Long delete(Long memberId) {
        return memberRepository.delete(memberId);
    }

    private void validateAdult(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears() + 1;
        if (age < 20) {
            throw new UnderageException(age);
        }
    }

    @Override
    public void checkEmailDuplicate(String email) {
        validateDuplicateEmail(email);
    }
}
