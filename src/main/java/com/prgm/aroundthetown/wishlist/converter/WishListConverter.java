package com.prgm.aroundthetown.wishlist.converter;

import com.prgm.aroundthetown.member.dto.MemberResponseDto;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.dto.ProductDto;
import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.wishlist.dto.WishListResponseDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishListConverter {
    public WishList toEntity(final Product productEntity,
                             final Member memberEntity) {
        return WishList.builder()
                .product(productEntity)
                .member(memberEntity)
                .build();
    }

    public WishListResponseDto toResponseDto(final Long wishListId,
                                             final MemberResponseDto memberDto,
                                             final ProductDto productDto) {
        return WishListResponseDto.builder()
                .wishListId(wishListId)
                .memberResponseDto(memberDto)
                .productDto(productDto)
                .build();
    }
}
