package com.prgm.aroundthetown.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostCreateResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

}
