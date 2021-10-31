package com.prgm.aroundthetown.cart.converter;

import com.prgm.aroundthetown.cart.dto.CartCreateDto;
import com.prgm.aroundthetown.cart.dto.CartDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
    public CartDto toDto(final Cart entity) {
        return CartDto.builder()
                .cartId(entity.getCartId())
                .product(entity.getProduct())
                .member(entity.getMember())
                .build();
    }

    public Cart toEntity(final CartCreateDto dto) {
        return Cart.builder()
                .product(dto.getProduct())
                .member(dto.getMember())
                .build();
    }
}
