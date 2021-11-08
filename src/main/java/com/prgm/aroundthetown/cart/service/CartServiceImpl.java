package com.prgm.aroundthetown.cart.service;

import com.prgm.aroundthetown.cart.converter.CartConverter;
import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartResponseDto;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.cart.repository.CartRepository;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.ProductRepository;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import com.prgm.aroundthetown.product.dto.ProductDto;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final CartConverter cartConverter;
    private final ProductConverter productConverter;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public Long createCart(final CartCreateRequestDto dto) {
        final Product productEntity = productRepository.getById(dto.getProductId());
        final Member memberEntity = memberRepository.getById(dto.getMemberId());
        return cartRepository
                .save(cartConverter.toEntity(productEntity, memberEntity, dto.getCount()))
                .getCartId();
    }

    @Override
    public CartResponseDto findById(final Long cartId) throws Exception {
        final Cart entity = cartRepository.getById(cartId);
        final ProductDto productDto = productConverter.toDto(entity.getProduct());
        final MemberResponseDto memberDto = memberConverter.toDto(entity.getMember());
        return cartConverter.toResponseDto(cartId, memberDto, productDto, entity.getCount());
    }

    @Override
    public List<CartResponseDto> findAll(final Long memberId) throws Exception {
        return memberRepository.getById(memberId)
                .getCarts()
                .stream()
                .map(cart -> cartConverter.toResponseDto(
                        cart.getCartId(),
                        memberConverter.toDto(cart.getMember()),
                        productConverter.toDto(cart.getProduct()),
                        cart.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartResponseDto deleteCart(final Long cartId) {
        final Cart entity = cartRepository.getById(cartId);
        entity.setIsDeleted(true);
        cartRepository.save(entity);
        final ProductDto productDto = productConverter.toDto(entity.getProduct());
        final MemberResponseDto memberDto = memberConverter.toDto(entity.getMember());
        return cartConverter.toResponseDto(cartId, memberDto, productDto, entity.getCount());
    }
}
