package com.prgm.aroundthetown.cart.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CartCreateRequestDto {
    private Long productId;
    private Long memberId;
}
