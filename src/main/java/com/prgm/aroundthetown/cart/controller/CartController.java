package com.prgm.aroundthetown.cart.controller;

import com.prgm.aroundthetown.cart.dto.CartCreateRequestDto;
import com.prgm.aroundthetown.cart.dto.CartFindByIdResponseDto;
import com.prgm.aroundthetown.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @PostMapping("/cart")
    public ResponseEntity<Long> createCart(
            @RequestBody final CartCreateRequestDto req
    ) {
        final Long cartIdResponse = service.createCart(req);
        return new ResponseEntity<>(cartIdResponse, HttpStatus.CREATED);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<CartFindByIdResponseDto> findById(
            @PathVariable final Long cartId
    ) {
        final CartFindByIdResponseDto res = service.findById(cartId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity deleteCart(
            @PathVariable final Long cartId
    ) {
        service.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
