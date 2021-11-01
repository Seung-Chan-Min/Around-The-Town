package com.prgm.aroundthetown.cart.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CartFindByIdResponseDto {
    private Long cartId;
    private Long productId;
    private Long memberId;
}
