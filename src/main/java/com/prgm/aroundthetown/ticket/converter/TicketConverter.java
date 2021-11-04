package com.prgm.aroundthetown.ticket.converter;

import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponseDto;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    public Ticket toTicket(TicketCreateRequestDto dto, Leisure leisure) {
        return Ticket.builder()
            .leisure(leisure)
            .ticketName(dto.getTicketName())
            .price(dto.getPrice())
            .build();
    }

    public TicketResponseDto toFindByIdResponse(Ticket ticket) {
        return TicketResponseDto.builder()
            .id(ticket.getId())
            .ticketName(ticket.getTicketName())
            .price(ticket.getPrice())
            .build();
    }

    public TicketDeleteResponseDto toDeleteByIdResponse(Ticket ticket) {
        return TicketDeleteResponseDto.builder()
            .id(ticket.getId())
            .ticketName(ticket.getTicketName())
            .price(ticket.getPrice())
            .build();
    }

    public TicketUpdateResponseDto toUpdateResponse(Ticket ticket) {
        return TicketUpdateResponseDto.builder()
            .id(ticket.getId())
            .ticketName(ticket.getTicketName())
            .price(ticket.getPrice())
            .build();
    }
}
