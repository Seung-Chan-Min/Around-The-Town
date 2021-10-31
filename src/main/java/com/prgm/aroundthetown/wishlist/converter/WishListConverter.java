package com.prgm.aroundthetown.wishlist.converter;

import com.prgm.aroundthetown.wishlist.dto.WishListCreateDto;
import com.prgm.aroundthetown.wishlist.dto.WishListDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import org.springframework.stereotype.Component;

@Component
public class WishListConverter {
    public WishListDto toDto(final WishList entity) {
        return WishListDto.builder()
                .wishListId(entity.getWishlistId())
                .product(entity.getProduct())
                .member(entity.getMember())
                .build();
    }

    public WishList toEntity(final WishListCreateDto dto) {
        return WishList.builder()
                .product(dto.getProduct())
                .member(dto.getMember())
                .build();
    }
}
