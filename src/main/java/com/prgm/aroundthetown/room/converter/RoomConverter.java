package com.prgm.aroundthetown.room.converter;

import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.dto.RoomCreateResponseDto;
import com.prgm.aroundthetown.room.dto.RoomResponseDto;
import com.prgm.aroundthetown.room.entity.Room;
import org.springframework.stereotype.Component;

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

    public RoomResponseDto entityToRoomResponseDto(
            final int remains,
            final Room room,
            final Accommodation accommodation
    ) {
        return RoomResponseDto.builder()
                .roomName(room.getRoomName())
                .reservationNotice(room.getReservationNotice())
                .roomInformation(room.getRoomInformation())
                .standardPeople(room.getStandardPeople())
                .maximumPeople(room.getMaximumPeople())
                .price(room.getPrice())
                .remains(remains)
                .accommodationDto(AccommodationDto.builder()
                        .accommodationName(accommodation.getAccommodationName())
                        .accommodationCategory(accommodation.getAccommodationCategory())
                        .accommodationNotice(accommodation.getAccommodationNotice())
                        .optionNotice(accommodation.getOptionNotice())
                        .guide(accommodation.getGuide())
                        .build())
                .build();
    }
}