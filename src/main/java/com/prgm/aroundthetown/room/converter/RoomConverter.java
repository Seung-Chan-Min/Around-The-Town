package com.prgm.aroundthetown.room.converter;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.room.dto.RequestCreateRoomDto;
import com.prgm.aroundthetown.room.dto.ResponseCreateRoomDto;
import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoomConverter {
    public Room requestCreateRoomDtoToEntity(
            final RequestCreateRoomDto requestCreateRoomDto,
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
                //.reservationNotice(initRoomRemains(requestCreateRoomDto.getMaxStock()))
                .build();
    }

    private List<RoomReservation> initRoomRemains(final int maxStock) {
        final List<RoomReservation> roomReservations = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            final RoomReservation build = RoomReservation.builder()
                    .dates(LocalDateTime.now().plusDays(i))
                    .remains(maxStock)
                    .build();
            roomReservations.add(build);
        }
        return roomReservations;
    }

    public ResponseCreateRoomDto entityToResponseCreateDto(final Room room) {
        return ResponseCreateRoomDto.builder()
                .roomName(room.getRoomName())
                .build();
    }
}