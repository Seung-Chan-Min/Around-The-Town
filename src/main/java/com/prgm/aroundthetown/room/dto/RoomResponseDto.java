package com.prgm.aroundthetown.room.dto;

import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RoomResponseDto {
    private AccommodationDto accommodationDto;
    private String roomName;
    private String reservationNotice;
    private String roomInformation;
    private int standardPeople;
    private int maximumPeople;
    private int price;
    private int remains;
}
