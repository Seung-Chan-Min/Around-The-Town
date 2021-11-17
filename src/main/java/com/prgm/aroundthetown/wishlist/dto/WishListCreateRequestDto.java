package com.prgm.aroundthetown.wishlist.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class WishListCreateRequestDto {
    private Long productId;
    private Long memberId;
}
