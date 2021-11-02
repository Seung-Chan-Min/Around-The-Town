package com.prgm.aroundthetown.order.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class OrderCreateRequestDto {
    private Long memberId;
    private List<OrderProductCreateRequestDto> orderProductCreateRequestDtos = new ArrayList<>();
}
