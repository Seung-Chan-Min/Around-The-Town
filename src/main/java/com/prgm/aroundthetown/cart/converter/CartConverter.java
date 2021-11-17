package com.prgm.aroundthetown.cart.converter;

import com.prgm.aroundthetown.cart.dto.CartResponseDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.dto.ProductDto;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartConverter {
    public Cart toEntity(final Product productEntity,
                         final Member memberEntity,
                         final int count) {
        return Cart.builder()
                .product(productEntity)
                .member(memberEntity)
                .count(count)
                .build();
    }

    public CartResponseDto toResponseDto(final Long cartId,
                                         final MemberResponseDto memberResponseDto,
                                         final ProductDto productDto,
                                         final int count) {
        return CartResponseDto.builder()
                .cartId(cartId)
                .memberResponseDto(memberResponseDto)
                .productDto(productDto)
                .count(count)
                .build();
    }
}
