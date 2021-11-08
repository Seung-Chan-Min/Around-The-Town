package com.prgm.aroundthetown.wishlist.dto;

import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.product.dto.ProductDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class WishListResponseDto {
    private Long wishListId;
    private MemberResponseDto memberDto;
    private ProductDto productDto;
}
