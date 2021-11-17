package com.prgm.aroundthetown.wishlist.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
import com.prgm.aroundthetown.wishlist.dto.WishListCreateRequestDto;
import com.prgm.aroundthetown.wishlist.dto.WishListResponseDto;
import com.prgm.aroundthetown.wishlist.service.WishListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WishListController {
    private final WishListServiceImpl service;

    @PostMapping("/wishList")
    public ResponseEntity<ApiResponse<Long>> createWishList(
            @RequestBody final WishListCreateRequestDto req
    ) {
        return ResponseEntity.ok(ApiResponse.created(service.createWishList(req)));
    }

    @GetMapping("/wishLists/{wishListId}")
    public ResponseEntity<ApiResponse<WishListResponseDto>> findById(
            @PathVariable final Long wishListId
    ) throws Exception {
        return ResponseEntity.ok(ApiResponse.ok(service.findById(wishListId)));
    }

    @GetMapping("/wishLists")
    public ResponseEntity<ApiResponse<List<WishListResponseDto>>> findAll(
            @RequestParam final Long memberId
    ) throws Exception {
        return ResponseEntity.ok(ApiResponse.ok(service.findAll(memberId)));
    }

    @DeleteMapping("/wishLists/{wishListId}")
    public ResponseEntity<ApiResponse<WishListResponseDto>> deleteWishList(
            @PathVariable final Long wishListId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(service.deleteWishList(wishListId)));
    }
}
