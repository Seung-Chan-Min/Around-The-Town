package com.prgm.aroundthetown.room.service;

import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.dto.RoomCreateResponseDto;

public interface RoomService {
    RoomCreateResponseDto saveRoom(RoomCreateRequestDto requestCreateRoomDto, Long productId);

    void getRoomsByCheckinAndCheckOut();
}
