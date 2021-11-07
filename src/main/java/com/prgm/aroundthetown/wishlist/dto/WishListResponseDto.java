package com.prgm.aroundthetown.wishlist.dto;

import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.product.dto.ProductDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class WishListResponseDto {
    private Long wishListId;
    private MemberDto memberDto;
    private ProductDto productDto;
}
