package org.sopt.service;

import org.sopt.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    public Long join(String name);

    public Optional<Member> findOne(Long memberId);

    public List<Member> findAllMembers();
}
