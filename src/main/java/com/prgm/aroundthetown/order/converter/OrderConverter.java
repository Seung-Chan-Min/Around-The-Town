package com.prgm.aroundthetown.order.converter;

import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderDto;
import com.prgm.aroundthetown.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final MemberRepository memberRepository;
    private final OrderProductConverter orderProductConverter;

    public Order toEntity(final OrderCreateRequestDto dto) {
        return Order.builder()
                .member(memberRepository.getById(dto.getMemberId()))
                .build();
    }

    public OrderDto toDto(final Order entity) {
        return OrderDto.builder()
                .orderId(entity.getOrderId())
                .memberId(entity.getMember().getId())
                .orderProductDtos(orderProductConverter.toDtos(entity.getOrderProducts()))
                .build();
    }
}
