package com.prgm.aroundthetown.cart.service;

import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartResponseDto;

import java.util.List;

public interface CartService {
    Long createCart(final CartCreateRequestDto dto);

    CartResponseDto findById(final Long cartId) throws Exception;

    List<CartResponseDto> findAll(final Long memberId) throws Exception;

    CartResponseDto deleteCart(final Long cartId);
}
