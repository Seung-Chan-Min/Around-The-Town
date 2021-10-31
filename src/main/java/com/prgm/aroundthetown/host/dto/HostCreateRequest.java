package com.prgm.aroundthetown.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostCreateRequest {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}
