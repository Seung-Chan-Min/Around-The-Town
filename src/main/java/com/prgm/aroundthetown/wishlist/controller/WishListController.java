package com.prgm.aroundthetown.wishlist.controller;

import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListFindByIdResponseDto;
import com.prgm.aroundthetown.wishlist.service.WishListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WishListController {
    private final WishListServiceImpl service;

    @PostMapping("/wishList")
    public ResponseEntity<Long> createWishList(
            @RequestBody final WishListCreateRequestDto req
    ) {
        final Long wishListIdResponse = service.createWishList(req);
        return new ResponseEntity<>(wishListIdResponse, HttpStatus.CREATED);
    }

    @GetMapping("/wishList/{wishListId}")
    public ResponseEntity<WishListFindByIdResponseDto> findById(
            @PathVariable final Long wishListId
    ) throws Exception {
        final WishListFindByIdResponseDto res = service.findById(wishListId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/wishList/{wishListId}")
    public ResponseEntity deleteWishList(
            @PathVariable final Long wishListId
    ) {
        service.deleteWishList(wishListId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
