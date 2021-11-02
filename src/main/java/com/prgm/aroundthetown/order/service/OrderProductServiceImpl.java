package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.order.converter.OrderProductConverter;
import com.prgm.aroundthetown.order.dto.OrderProductCreateRequestDto;
import com.prgm.aroundthetown.order.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderProductConverter orderProductConverter;

    public Long createOrderProduct(final OrderProductCreateRequestDto dto, final Long orderId) { // request는 productId
        return orderProductRepository.save(orderProductConverter.toEntity(dto, orderId)).getId();
    }
}
