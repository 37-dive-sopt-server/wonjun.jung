package org.sopt.repository;

import org.sopt.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Member save(Member member);

    public Optional<Member> findById(Long id);

    public List<Member> findAll();

    public Optional<Member> findByEmail(String email);

    public Long delete(Long id);
}
