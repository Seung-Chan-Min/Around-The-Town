package com.prgm.aroundthetown.room.service;

import com.prgm.aroundthetown.room.dto.RequestCreateRoomDto;
import com.prgm.aroundthetown.room.dto.ResponseCreateRoomDto;

public interface RoomService {
    ResponseCreateRoomDto saveRoom(RequestCreateRoomDto requestCreateRoomDto, Long productId);
}
