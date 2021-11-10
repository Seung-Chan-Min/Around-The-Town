package com.prgm.aroundthetown.member.service;

import com.prgm.aroundthetown.member.dto.MemberCreateRequestDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByEmailResponseDto;
import com.prgm.aroundthetown.member.dto.MemberFindByPhoneNumberResponseDto;
import com.prgm.aroundthetown.member.dto.MemberUpdateRequestDto;

public interface MemberService {
    Long createMember(final MemberCreateRequestDto dto);
    MemberResponseDto findById(final Long memberId);
    MemberFindByEmailResponseDto findByEmail(final String email);
    MemberFindByPhoneNumberResponseDto findByPhoneNumber(final String phoneNumber);
    Long updateMember(final Long memberId, final MemberUpdateRequestDto dto);
    Long deleteMember(final Long memberId);
}
