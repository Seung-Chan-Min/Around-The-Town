package com.prgm.aroundthetown.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MemberCreateRequestDto {
    private String password;
    private String phoneNumber;
    private String email;
}
