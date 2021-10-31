package com.prgm.aroundthetown.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RequestCreateRoomDto {
    private String roomName;
    private String reservationNotice;
    private String roomInformation;
    private int standardPeople;
    private int maximumPeople;
    private int price;
    private int maxStock;
}
