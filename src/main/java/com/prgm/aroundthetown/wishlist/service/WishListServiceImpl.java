package com.prgm.aroundthetown.wishlist.service;

import com.prgm.aroundthetown.wishlist.converter.WishListConverter;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListFindByIdResponseDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import com.prgm.aroundthetown.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListServiceImpl implements WishListService {
    private final WishListRepository repository;
    private final WishListConverter converter;

    @Transactional
    public Long createWishList(final WishListCreateRequestDto dto) {
        return repository.save(converter.toEntity(dto)).getWishlistId();
    }

    public WishListFindByIdResponseDto findById(final Long wishListId) throws Exception {
        return converter.toFindByIdDto(repository.getById(wishListId));
    }

    @Transactional
    public void deleteWishList(final Long wishListId) {
        final WishList entity = repository.findById(wishListId).get();
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
