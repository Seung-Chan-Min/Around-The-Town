package com.prgm.aroundthetown.cart.service;

import com.prgm.aroundthetown.cart.converter.CartConverter;
import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartFindByIdResponseDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final CartConverter converter;

    @Transactional
    public Long createCart(final CartCreateRequestDto dto) {
        return repository.save(converter.toEntity(dto)).getCartId();
    }

    public CartFindByIdResponseDto findById(final Long cartId) throws Exception {
        return converter.toFindByIdDto(repository.getById(cartId));
    }

    @Transactional
    public void deleteCart(final Long cartId) {
        final Cart entity = repository.getById(cartId);
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
