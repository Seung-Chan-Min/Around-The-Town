package com.prgm.aroundthetown.cart.dto;

import com.prgm.aroundthetown.host.dto.HostDto;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.product.dto.ProductDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CartFindByIdResponseDto {
    private Long cartId;
    private HostDto hostDto;
    private ProductDto productDto;
    private MemberDto memberDto;
}
