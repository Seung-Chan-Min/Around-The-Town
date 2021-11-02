package com.prgm.aroundthetown.order.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class OrderProductDto {
    private Long id;
    private Long productId;
    private Long orderId;
    private int count;
}
