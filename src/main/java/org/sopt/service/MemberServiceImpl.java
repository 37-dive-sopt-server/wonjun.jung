package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.repository.MemoryMemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private static long sequence = 1L;

    public Long join(String name, LocalDate birthday, String email, Sex sex) {
        validateDuplicateEmail(email);

        Member member = new Member(sequence++, name, birthday, email, sex);
        memberRepository.save(member);
        return member.getId();
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
                throw new IllegalStateException("⚠️ 이미 존재하는 이메일입니다.");
            });
    }

    public Long delete(Long memberId) {
        return memberRepository.delete(memberId);
    }
}
