package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderFindByIdResponseDto;
import com.prgm.aroundthetown.order.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    Long createOrder(final OrderCreateRequestDto orderDto);

    OrderFindByIdResponseDto findById(final Long orderId);

    List<OrderResponseDto> findAllOrdersByMember(final Long memberId);

    OrderResponseDto deleteOrder(final Long orderId);
}
