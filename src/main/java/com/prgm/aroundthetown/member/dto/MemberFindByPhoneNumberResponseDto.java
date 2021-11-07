package com.prgm.aroundthetown.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MemberFindByPhoneNumberResponseDto {
    private Long memberId;
    private String password;
    private String phoneNumber;
    private String email;
}
