package com.prgm.aroundthetown.cart.service;

import com.prgm.aroundthetown.cart.converter.CartConverter;
import com.prgm.aroundthetown.cart.dto.CartCreateDto;
import com.prgm.aroundthetown.cart.dto.CartDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.cart.repository.CartRepository;
import com.prgm.aroundthetown.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository repository;
    private final CartConverter converter;

    @Transactional
    public Long createCart(final CartCreateDto dto) {
        return repository.save(converter.toEntity(dto)).getCartId();
    }

    public CartDto findById(final Long cartId) {
        return repository.findById(cartId)
                .map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("Cart is not found"));
    }

    @Transactional
    public void deleteCart(final Long cartId) {
        final Cart entity = repository.findById(cartId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
