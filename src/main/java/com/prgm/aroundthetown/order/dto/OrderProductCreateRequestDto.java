package com.prgm.aroundthetown.order.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class OrderProductCreateRequestDto {
    private Long productId;
    private int count;
}
