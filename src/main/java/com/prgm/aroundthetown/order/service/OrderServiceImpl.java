package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.order.converter.OrderConverter;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderDto;
import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductServiceImpl orderProductService;
    private final OrderConverter orderConverTer;

    @Transactional
    public Long createOrder(final OrderCreateRequestDto orderDto) {
        final Order orderEntity = orderRepository.save(orderConverTer.toEntity(orderDto));
        final Long orderId = orderEntity.getOrderId();

        for (final OrderProductCreateRequestDto orderProductCreateDto : orderDto.getOrderProductCreateRequestDtos()) {
            orderProductService.createOrderProduct(orderProductCreateDto, orderId);
        }

        return orderId;
    }

    public OrderDto findById(final Long orderId) {
        return orderConverTer.toDto(orderRepository.getById(orderId));
    }

//    @Transactional
//    public OrderDeleteResponseDto deleteOrder() {
//        
//    }
}
