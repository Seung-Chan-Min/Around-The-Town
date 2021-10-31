package com.prgm.aroundthetown.wishlist.dto;

import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class WishListDto {
    private Long wishListId;
    private Product product;
    private Member member;
}
