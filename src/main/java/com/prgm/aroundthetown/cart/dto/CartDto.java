package com.prgm.aroundthetown.cart.dto;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CartDto {
    private Long cartId;
    private Product product;
    private Member member;
}
