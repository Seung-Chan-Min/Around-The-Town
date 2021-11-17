package com.prgm.aroundthetown.order.dto;

import com.prgm.aroundthetown.product.dto.ProductDto;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class OrderProductDto {
    private int count;
    private ProductDto productDto;
}
