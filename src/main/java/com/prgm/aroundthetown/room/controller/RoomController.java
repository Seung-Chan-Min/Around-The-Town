package com.prgm.aroundthetown.room.controller;

import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.dto.RoomCreateResponseDto;
import com.prgm.aroundthetown.room.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RoomController {

    private RoomService roomService;

    @PostMapping("/hosts/accommodations/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoomCreateResponseDto> saveRoom(
            @PathVariable final Long productId,
            @RequestBody final RoomCreateRequestDto roomCreateDto
    ) {
        RoomCreateResponseDto response = roomService.saveRoom(roomCreateDto, productId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}