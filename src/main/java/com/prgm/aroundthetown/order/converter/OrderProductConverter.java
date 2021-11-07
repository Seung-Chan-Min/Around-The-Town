package com.prgm.aroundthetown.order.converter;

import com.prgm.aroundthetown.order.dto.OrderProductDto;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderProductConverter {
    private final ProductConverter productConverter;

    public OrderProduct toEntity(final Product product,
                                 final Order order,
                                 final int count) {
        return OrderProduct.builder()
                .product(product)
                .order(order)
                .count(count)
                .build();
    }

    public List<OrderProductDto> toDtos(final List<OrderProduct> orderProducts) {
        return orderProducts
                .stream()
                .map(orderProduct -> OrderProductDto.builder()
                        .count(orderProduct.getCount())
                        .productDto(productConverter.toDto(orderProduct.getProduct()))
                        .build())
                .collect(Collectors.toList());
    }
}
