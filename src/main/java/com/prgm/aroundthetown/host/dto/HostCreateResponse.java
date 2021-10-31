package com.prgm.aroundthetown.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostCreateResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

}
