package com.prgm.aroundthetown.order.service;

import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.order.converter.OrderConverter;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderFindByIdResponseDto;
import com.prgm.aroundthetown.order.dto.OrderResponseDto;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    private final OrderConverter orderConverTer;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public Long createOrder(final OrderCreateRequestDto orderDto) {
        return orderRepository.save(Order.builder()
                        .member(memberRepository.getById(orderDto.getMemberId()))
                        .build())
                .getOrderId();
    }

    @Override
    public OrderFindByIdResponseDto findById(final Long orderId) {
        final Order entity = orderRepository.getById(orderId);
        return orderConverTer.toFindByIdDto(memberConverter.toDto(entity.getMember()));
    }

    @Override
    public List<OrderResponseDto> findAllOrdersByMember(final Long memberId) {
        return memberRepository.getById(memberId)
                .getOrders()
                .stream()
                .map(orderConverTer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto deleteOrder(final Long orderId) {
        final Order entity = orderRepository.getById(orderId);
        entity.setIsDeleted(true);
        return orderConverTer.toDto(orderRepository.save(entity));
    }
}
