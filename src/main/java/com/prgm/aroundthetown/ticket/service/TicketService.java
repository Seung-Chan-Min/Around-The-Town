package com.prgm.aroundthetown.ticket.service;


import com.prgm.aroundthetown.ticket.dto.TicketCreateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketCreateResponse;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketFindByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponse;

public interface TicketService {

    TicketCreateResponse create(TicketCreateRequest dto);
    TicketFindByIdResponse findById(Long id);
    TicketUpdateResponse update(TicketUpdateRequest dto);
    TicketDeleteByIdResponse deleteById(Long id);


}
