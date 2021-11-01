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
public class TicketCreateResponse {
    private Long id;
    private String ticketName;
    private int price;
    private Long leisureId;
}
