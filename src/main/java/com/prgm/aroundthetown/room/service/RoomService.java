package com.prgm.aroundthetown.room.service;

import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.dto.RoomCreateResponseDto;
import com.prgm.aroundthetown.room.dto.RoomResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    RoomCreateResponseDto saveRoom(RoomCreateRequestDto requestCreateRoomDto, Long productId);

    List<RoomResponseDto> getRoomsByCheckinAndCheckOut(Long accommodationId, LocalDate checkIn);
}
