package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.order.converter.OrderProductConverter;
import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderProductDto;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.order.repository.OrderProductRepository;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import com.prgm.aroundthetown.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    private final OrderProductConverter orderProductConverter;

    public void createOrderProduct(final Long orderId,
                                   final List<OrderProductCreateRequestDto> dtos) {
        for (final OrderProductCreateRequestDto orderProductCreateDto : dtos) {
            orderProductRepository.save(
                    orderProductConverter.toEntity(
                            productRepository.getById(orderProductCreateDto.getProductId()),
                            orderRepository.getById(orderId),
                            orderProductCreateDto.getCount()));
        }
    }

    public List<OrderProductDto> getOrderProductDtos(final Long orderId) {
        final Order entity = orderRepository.getById(orderId);
        return orderProductConverter.toDtos(entity.getOrderProducts());
    }
}
