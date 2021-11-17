package com.prgm.aroundthetown.wishlist.service;

import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListResponseDto;

import java.util.List;

public interface WishListService {
    Long createWishList(final WishListCreateRequestDto dto);

    WishListResponseDto findById(final Long wishListId) throws Exception;

    List<WishListResponseDto> findAll(final Long memberId) throws Exception;

    WishListResponseDto deleteWishList(final Long wishListId);
}
