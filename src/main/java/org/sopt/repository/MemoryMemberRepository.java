package org.sopt.repository;

import org.sopt.domain.Member;
import org.sopt.exception.MemberNotFoundException;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (member.getId() == null || member.getId() == 0L) {
            member.setId(++sequence);
        }
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
            .filter(member -> member.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    @Override
    public Long delete(Long id) {
        Member member = store.get(id);
        if (member == null) {
            throw new MemberNotFoundException(id);
        }
        store.remove(id);
        return member.getId();
    }
}
