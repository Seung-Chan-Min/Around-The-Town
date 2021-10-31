package com.prgm.aroundthetown.room.controller;

import com.prgm.aroundthetown.room.dto.RequestCreateRoomDto;
import com.prgm.aroundthetown.room.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoomController {

    private RoomService roomService;

    @PostMapping("/accommodation/{productId}")
    public void saveRoom(
            @PathVariable final Long productId,
            @RequestBody final RequestCreateRoomDto roomCreateDto
    ) {
        roomService.saveRoom(roomCreateDto, productId);
    }
}