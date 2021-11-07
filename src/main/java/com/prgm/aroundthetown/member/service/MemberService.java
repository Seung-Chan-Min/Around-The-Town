package com.prgm.aroundthetown.member.service;

import com.prgm.aroundthetown.member.dto.MemberCreateDto;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.dto.MemberFindByEmailResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByPhoneNumberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateDto;

public interface MemberService {
    Long createMember(final MemberCreateDto dto);
    MemberDto findById(final Long memberId);
    MemberFindByEmailResponseDto findByEmail(final String email);
    MemberFindByPhoneNumberResponseDto findByPhoneNumber(final String phoneNumber);
    Long updateMember(final Long memberId, final MemberUpdateDto dto);
    void deleteMember(final Long memberId);
}
