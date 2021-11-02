package com.prgm.aroundthetown.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long memberId;
    private String password;
    private String phoneNumber;
    private String email;
}
