package com.prgm.aroundthetown.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostCreateRequest {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}
