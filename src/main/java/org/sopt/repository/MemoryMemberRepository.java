package org.sopt.repository;

import org.sopt.common.ErrorCode;
import org.sopt.domain.Member;
import org.sopt.exception.BusinessException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("memoryMemberRepository")
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (member.getId() == null || member.getId() == 0L) {
            Long newId = ++sequence;
            Member newMember = new Member(
                    newId,
                    member.getName(),
                    member.getBirthDate(),
                    member.getEmail(),
                    member.getSex()
            );
            store.put(newId, newMember);
            return newMember;
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
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        store.remove(id);
        return member.getId();
    }

    @Override
    public Member update(Member member) {
        if (!store.containsKey(member.getId())) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        store.put(member.getId(), member);
        return member;
    }
}
