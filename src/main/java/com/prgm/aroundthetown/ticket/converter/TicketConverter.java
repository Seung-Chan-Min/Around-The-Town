package com.prgm.aroundthetown.ticket.converter;

import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketFindByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponse;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    public Ticket toTicket(TicketCreateRequest dto, Leisure leisure) {
        return Ticket.builder()
            .leisure(leisure)
            .ticketName(dto.getTicketName())
            .price(dto.getPrice())
            .build();
    }

    public TicketFindByIdResponse toFindByIdResponse(Ticket ticket) {
        return TicketFindByIdResponse.builder()
            .id(ticket.getId())
            .ticketName(ticket.getTicketName())
            .price(ticket.getPrice())
            .build();
    }

    public TicketDeleteByIdResponse toDeleteByIdResponse(Ticket ticket) {
        return TicketDeleteByIdResponse.builder()
            .id(ticket.getId())
            .ticketName(ticket.getTicketName())
            .price(ticket.getPrice())
            .build();
    }

    public TicketUpdateResponse toUpdateResponse(Ticket ticket) {
        return TicketUpdateResponse.builder()
            .id(ticket.getId())
            .ticketName(ticket.getTicketName())
            .price(ticket.getPrice())
            .build();
    }
}
