package com.prgm.aroundthetown.wishlist.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.wishlist.converter.WishListConverter;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateDto;
import com.prgm.aroundthetown.wishlist.dto.WishListDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import com.prgm.aroundthetown.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {
    private final WishListRepository repository;
    private final WishListConverter converter;

    @Transactional
    public Long createWishList(final WishListCreateDto dto) {
        return repository.save(converter.toEntity(dto)).getWishlistId();
    }

    public WishListDto findById(final Long wishListId) {
        return repository.findById(wishListId)
                .map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("WishList is not found"));
    }

    @Transactional
    public void deleteWishList(final Long wishListId) {
        final WishList entity = repository.findById(wishListId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
