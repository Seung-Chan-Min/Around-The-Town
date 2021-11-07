package com.prgm.aroundthetown.order.controller;

import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderFindByIdResponseDto;
import com.prgm.aroundthetown.order.dto.OrderResponseDto;
import com.prgm.aroundthetown.order.service.OrderProductServiceImpl;
import com.prgm.aroundthetown.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final OrderProductServiceImpl orderProductService;

    @PostMapping("/order")
    public ResponseEntity<Long> createOrder(
            @RequestBody final OrderCreateRequestDto req
    ) {
        final Long orderId = orderService.createOrder(req);
        orderProductService.createOrderProduct(orderId, req.getOrderProductCreateRequestDtos());
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderFindByIdResponseDto> findById(
            @PathVariable final Long orderId
    ) {
        final OrderFindByIdResponseDto res = orderService.findById(orderId);
        res.setOrderProductDtos(orderProductService.getOrderProductDtos(orderId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> findAllByMember(
            @RequestParam final Long memberId
    ) {
        final List<OrderResponseDto> res = orderService.findAllOrdersByMember(memberId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> deleteOrder(
            @PathVariable final Long orderId
    ) {
        final OrderResponseDto res = orderService.deleteOrder(orderId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
