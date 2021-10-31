package com.prgm.aroundthetown.cart.dto;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CartCreateDto {
    private Product product;
    private Member member;
}
