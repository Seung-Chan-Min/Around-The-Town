package com.prgm.aroundthetown.wishlist.service;

import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.dto.MemberDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.ProductRepository;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import com.prgm.aroundthetown.product.dto.ProductDto;
import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.wishlist.converter.WishListConverter;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListResponseDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import com.prgm.aroundthetown.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListServiceImpl implements WishListService {
    private final WishListRepository repository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final WishListConverter wishListConverter;
    private final ProductConverter productConverter;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public Long createWishList(final WishListCreateRequestDto dto) {
        final Product productEntity = productRepository.getById(dto.getProductId());
        final Member memberEntity = memberRepository.getById(dto.getMemberId());
        return repository
                .save(wishListConverter.toEntity(productEntity, memberEntity))
                .getWishlistId();
    }

    @Override
    public WishListResponseDto findById(final Long wishListId) throws Exception {
        final WishList entity = repository.getById(wishListId);
        final ProductDto productDto = productConverter.toDto(entity.getProduct());
        final MemberDto memberDto = memberConverter.toDto(entity.getMember());
        return wishListConverter.toResponseDto(wishListId, memberDto, productDto);
    }

    @Override
    public List<WishListResponseDto> findAll(final Long memberId) throws Exception {
        return memberRepository.getById(memberId)
                .getWishLists()
                .stream()
                .map(wishList -> wishListConverter.toResponseDto(
                        wishList.getWishlistId(),
                        memberConverter.toDto(wishList.getMember()),
                        productConverter.toDto(wishList.getProduct())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WishListResponseDto deleteWishList(final Long wishListId) {
        final WishList entity = repository.getById(wishListId);
        entity.setIsDeleted(true);
        repository.save(entity);
        final ProductDto productDto = productConverter.toDto(entity.getProduct());
        final MemberDto memberDto = memberConverter.toDto(entity.getMember());
        return wishListConverter.toResponseDto(wishListId, memberDto, productDto);
    }
}
