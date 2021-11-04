package com.prgm.aroundthetown.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class TicketUpdateRequest {
    private Long id;
    private String ticketName;
    private Integer price;
}