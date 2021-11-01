package com.prgm.aroundthetown.cart.converter;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartFindByIdResponseDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import com.prgm.aroundthetown.product.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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

    public CartFindByIdResponseDto toFindByIdDto(final Cart entity) throws Exception {
        if (entity.getProduct().getClass().equals(Accommodation.class)) {
            return CartFindByIdResponseDto.builder()
                    .cartId(entity.getCartId())
                    .productDto(productConverter.accommodationToDto((Accommodation) entity.getProduct()))
                    .memberDto(memberConverter.toDto(entity.getMember()))
                    .build();
        } else if (entity.getProduct().getClass().equals(Leisure.class)) {
            return CartFindByIdResponseDto.builder()
                    .cartId(entity.getCartId())
                    .productDto(productConverter.leisureToDto((Leisure) entity.getProduct()))
                    .memberDto(memberConverter.toDto(entity.getMember()))
                    .build();
        } else throw new Exception("존재하지 않는 Product Type 입니다.");
    }
}
