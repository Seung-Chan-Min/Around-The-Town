package com.prgm.aroundthetown.wishlist.converter;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.member.converter.MemberConverter;
import com.prgm.aroundthetown.member.repository.MemberRepository;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import com.prgm.aroundthetown.product.entity.ProductRepository;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListFindByIdResponseDto;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishListConverter {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private final ProductConverter productConverter;
    private final MemberConverter memberConverter;

    public WishList toEntity(final WishListCreateRequestDto dto) {
        return WishList.builder()
                .product(productRepository.getById(dto.getProductId()))
                .member(memberRepository.getById(dto.getMemberId()))
                .build();
    }

    public WishListFindByIdResponseDto toFindByIdDto(final WishList entity) throws Exception {
        if (entity.getProduct().getClass().equals(Accommodation.class)) {
            return WishListFindByIdResponseDto.builder()
                    .wishListId(entity.getWishlistId())
                    .productDto(productConverter.accommodationToDto((Accommodation) entity.getProduct()))
                    .memberDto(memberConverter.toDto(entity.getMember()))
                    .build();
        } else if (entity.getProduct().getClass().equals(Leisure.class)) {
            return WishListFindByIdResponseDto.builder()
                    .wishListId(entity.getWishlistId())
                    .productDto(productConverter.leisureToDto((Leisure) entity.getProduct()))
                    .memberDto(memberConverter.toDto(entity.getMember()))
                    .build();
        } else throw new Exception("존재하지 않는 Product Type입니다.");
    }
}
