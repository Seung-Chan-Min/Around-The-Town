package com.prgm.aroundthetown.wishlist.dto;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class WishListCreateDto {
    private Product product;
    private Member member;
}
