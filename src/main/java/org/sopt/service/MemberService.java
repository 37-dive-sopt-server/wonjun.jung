package org.sopt.service;

import org.sopt.domain.Sex;
import org.sopt.dto.request.MemberUpdateRequest;
import org.sopt.dto.response.MemberResponse;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    MemberResponse join(String name, LocalDate birthday, String email, Sex sex);

    MemberResponse findOne(Long memberId);

    List<MemberResponse> findAllMembers();

    void delete(Long memberId);

    MemberResponse update(Long memberId, MemberUpdateRequest req);
}
