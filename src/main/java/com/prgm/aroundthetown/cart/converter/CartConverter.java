package com.prgm.aroundthetown.cart.converter;

import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartFindByIdResponseDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public Cart toEntity(final CartCreateRequestDto dto) {
        return Cart.builder()
                .product(productRepository.getById(dto.getProductId()))
                .member(memberRepository.getById(dto.getMemberId()))
                .build();
    }

    public CartFindByIdResponseDto toFindByIdDto(final Cart entity) {
        return CartFindByIdResponseDto.builder()
                .cartId(entity.getCartId())
                .productId(entity.getProduct().getProductId())
                .memberId(entity.getMember().getId())
                .build();
    }

}
