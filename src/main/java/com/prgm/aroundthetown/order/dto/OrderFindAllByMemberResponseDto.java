package com.prgm.aroundthetown.order.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class OrderFindAllByMemberResponseDto {
    private List<OrderResponseDto> orderResponseDtos;
}
