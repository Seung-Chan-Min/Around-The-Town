package com.prgm.aroundthetown.order.converter;

import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final MemberRepository memberRepository;

    public Order toEntity(final OrderCreateRequestDto dto) {
        return Order.builder()
                .member(memberRepository.getById(dto.getMemberId()))
                .build();
    }
}
