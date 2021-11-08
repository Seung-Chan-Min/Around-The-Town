package com.prgm.aroundthetown.order.dto;

import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class OrderFindByIdResponseDto {
    private MemberResponseDto memberResponseDto;
    private List<OrderProductDto> orderProductDtos = new ArrayList<>();
}
