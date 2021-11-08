package com.prgm.aroundthetown.cart.controller;

import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartResponseDto;
import com.prgm.aroundthetown.cart.service.CartServiceImpl;
import com.prgm.aroundthetown.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceImpl service;

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<Long>> createCart(
            @RequestBody final CartCreateRequestDto req
    ) {
        return ResponseEntity.ok(ApiResponse.created(service.createCart(req)));
    }

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<ApiResponse<CartResponseDto>> findById(
            @PathVariable final Long cartId
    ) throws Exception {
        return ResponseEntity.ok(ApiResponse.ok(service.findById(cartId)));
    }

    @GetMapping("/carts")
    public ResponseEntity<ApiResponse<List<CartResponseDto>>> findAll(
            @RequestParam final Long memberId
    ) throws Exception {
        return ResponseEntity.ok(ApiResponse.ok(service.findAll(memberId)));
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<ApiResponse<CartResponseDto>> deleteCart(
            @PathVariable final Long cartId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(service.deleteCart(cartId)));
    }
}
