package com.prgm.aroundthetown.ticket.service;


import com.prgm.aroundthetown.ticket.dto.TicketCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponseDto;

public interface TicketService {

    Long create(TicketCreateRequestDto dto);
    TicketResponseDto findById(Long id);
    TicketUpdateResponseDto update(TicketUpdateRequestDto dto);
    TicketDeleteResponseDto deleteById(Long id);
}
