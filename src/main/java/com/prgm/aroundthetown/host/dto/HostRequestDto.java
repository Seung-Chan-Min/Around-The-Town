package com.prgm.aroundthetown.host.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class HostRequestDto { //조회에 쓰이는것..?
    private String hostName;
    private String hostEmail;
    private String hostPhoneNumber;
//    private List<Product> products;
}
