package com.prgm.aroundthetown.cart.converter;

import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartFindByIdResponseDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.ProductRepository;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final ProductConverter productConverter;
    private final MemberConverter memberConverter;

    public Cart toEntity(final CartCreateRequestDto dto) {
        return Cart.builder()
                .product(productRepository.getById(dto.getProductId()))
                .member(memberRepository.getById(dto.getMemberId()))
                .build();
    }

    public CartFindByIdResponseDto toFindByIdDto(final Cart entity) {
        return CartFindByIdResponseDto.builder()
                .cartId(entity.getCartId())
                .productDto(productConverter.toDto(entity.getProduct()))
                .memberResponseDto(memberConverter.toDto(entity.getMember()))
                .build();
    }
}
