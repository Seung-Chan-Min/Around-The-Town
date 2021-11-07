package com.prgm.aroundthetown.cart.controller;

import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartResponseDto;
import com.prgm.aroundthetown.cart.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceImpl service;

    @PostMapping("/cart")
    public ResponseEntity<Long> createCart(
            @RequestBody final CartCreateRequestDto req
    ) {
        final Long cartIdResponse = service.createCart(req);
        return new ResponseEntity<>(cartIdResponse, HttpStatus.CREATED);
    }

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<CartResponseDto> findById(
            @PathVariable final Long cartId
    ) throws Exception {
        final CartResponseDto res = service.findById(cartId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponseDto>> findAll(
            @RequestParam final Long memberId
    ) throws Exception {
        final List<CartResponseDto> res = service.findAll(memberId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<CartResponseDto> deleteCart(
            @PathVariable final Long cartId
    ) {
        final CartResponseDto res = service.deleteCart(cartId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
