package com.prgm.aroundthetown.order.converter;

import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderProductDto;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import com.prgm.aroundthetown.product.ProductRepository;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderProductConverter {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductConverter productConverter;

    public OrderProduct toEntity(final OrderProductCreateRequestDto dto, final Long orderId) {
        return OrderProduct.builder()
                .product(productRepository.getById(dto.getProductId()))
                .order(orderRepository.getById(orderId))
                .count(dto.getCount())
                .build();
    }

    public List<OrderProductDto> toDtos(final List<OrderProduct> orderProducts) {
        return orderProducts
                .stream()
                .map(orderProduct -> OrderProductDto.builder()
                        .orderId(orderProduct.getOrder().getOrderId())
                        .count(orderProduct.getCount())
                        .productDto(productConverter.toDto(orderProduct.getProduct()))
                        .build())
                .collect(Collectors.toList());
    }

}
