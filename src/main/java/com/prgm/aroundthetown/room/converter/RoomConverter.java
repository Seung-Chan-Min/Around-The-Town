package com.prgm.aroundthetown.room.converter;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.dto.RoomCreateResponseDto;
import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoomConverter {
    public Room requestCreateRoomDtoToEntity(
            final RoomCreateRequestDto requestCreateRoomDto,
            final Accommodation accommodation
    ) {
        return Room.builder()
                .accommodation(accommodation)
                .roomName(requestCreateRoomDto.getRoomName())
                .reservationNotice(requestCreateRoomDto.getReservationNotice())
                .roomInformation(requestCreateRoomDto.getRoomInformation())
                .price(requestCreateRoomDto.getPrice())
                .standardPeople(requestCreateRoomDto.getStandardPeople())
                .maximumPeople(requestCreateRoomDto.getMaximumPeople())
                .stock(requestCreateRoomDto.getMaxStock())
                .build();
    }


    public RoomCreateResponseDto entityToResponseCreateDto(final Room room) {
        return RoomCreateResponseDto.builder()
                .roomName(room.getRoomName())
                .build();
    }
}