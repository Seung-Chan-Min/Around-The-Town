package com.prgm.aroundthetown.order.converter;

import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.order.dto.OrderFindByIdResponseDto;
import com.prgm.aroundthetown.order.dto.OrderResponseDto;
import com.prgm.aroundthetown.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderProductConverter orderProductConverter;

    public OrderResponseDto toDto(final Order entity) {
        return OrderResponseDto.builder()
                .orderId(entity.getOrderId())
                .memberId(entity.getMember().getId())
                .orderProductDtos(orderProductConverter.toDtos(entity.getOrderProducts()))
                .build();
    }

    public OrderFindByIdResponseDto toFindByIdDto(final MemberResponseDto memberResponseDto) {
        return OrderFindByIdResponseDto.builder()
                .memberResponseDto(memberResponseDto)
                .build();
    }
}
