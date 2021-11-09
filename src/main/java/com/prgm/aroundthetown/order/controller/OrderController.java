package com.prgm.aroundthetown.order.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
import com.prgm.aroundthetown.order.dto.OrderCreateRequestDto;
import com.prgm.aroundthetown.order.dto.OrderFindByIdResponseDto;
import com.prgm.aroundthetown.order.dto.OrderResponseDto;
import com.prgm.aroundthetown.order.service.OrderProductServiceImpl;
import com.prgm.aroundthetown.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final OrderProductServiceImpl orderProductService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<Long>> createOrder(
            @RequestBody final OrderCreateRequestDto req
    ) {
        final Long orderId = orderService.createOrder(req);
        orderProductService.createOrderProduct(orderId, req.getOrderProductCreateRequestDtos());
        return ResponseEntity.ok(ApiResponse.created(orderId));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderFindByIdResponseDto>> findById(
            @PathVariable final Long orderId
    ) {
        final OrderFindByIdResponseDto res = orderService.findById(orderId);
        res.setOrderProductDtos(orderProductService.getOrderProductDtos(orderId));
        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> findAllByMember(
            @RequestParam final Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.findAllOrdersByMember(memberId)));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> deleteOrder(
            @PathVariable final Long orderId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.deleteOrder(orderId)));
    }
}
