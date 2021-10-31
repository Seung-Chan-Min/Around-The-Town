package com.prgm.aroundthetown.host.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class HostUpdateDto {
    private Long id;
    private String hostName;
    private String hostEmail;
    private String hostPhoneNumber;
}
