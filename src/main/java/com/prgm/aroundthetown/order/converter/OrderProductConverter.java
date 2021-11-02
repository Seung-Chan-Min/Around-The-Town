package com.prgm.aroundthetown.order.converter;

import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import com.prgm.aroundthetown.product.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProductConverter {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderProduct toEntity(final OrderProductCreateRequestDto dto, final Long orderId) {
        return OrderProduct.builder()
                .product(productRepository.getById(dto.getProductId()))
                .order(orderRepository.getById(orderId))
                .count(dto.getCount())
                .build();
    }

}
