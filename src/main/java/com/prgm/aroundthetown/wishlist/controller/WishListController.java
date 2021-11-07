package com.prgm.aroundthetown.wishlist.controller;

import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListResponseDto;
import com.prgm.aroundthetown.wishlist.service.WishListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
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

    @GetMapping("/wishLists/{wishListId}")
    public ResponseEntity<WishListResponseDto> findById(
            @PathVariable final Long wishListId
    ) throws Exception {
        final WishListResponseDto res = service.findById(wishListId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/wishLists")
    public ResponseEntity<List<WishListResponseDto>> findAll(
            @RequestParam final Long memberId
    ) throws Exception {
        final List<WishListResponseDto> res = service.findAll(memberId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/wishLists/{wishListId}")
    public ResponseEntity<WishListResponseDto> deleteWishList(
            @PathVariable final Long wishListId
    ) {
        final WishListResponseDto res = service.deleteWishList(wishListId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
