package com.prgm.aroundthetown.cart.dto;

import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.product.dto.ProductDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private Long cartId;
    private MemberResponseDto memberResponseDto;
    private ProductDto productDto;
    private int count;
}
