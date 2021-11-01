package com.prgm.aroundthetown.host.dto;

import com.prgm.aroundthetown.product.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class HostDto {
    private Long id;
    private String hostName;
    private String hostEmail;
    private String hostPhoneNumber;
    private List<Product> products;
}
