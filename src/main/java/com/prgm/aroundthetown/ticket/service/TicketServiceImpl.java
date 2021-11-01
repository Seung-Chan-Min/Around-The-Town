package com.prgm.aroundthetown.ticket.service;

import com.prgm.aroundthetown.ticket.converter.TicketConverter;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketFindByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponse;
import com.prgm.aroundthetown.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {

    private final TicketConverter ticketConverter;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public Long create(TicketCreateRequest dto) {
        return null;
    }

    @Override
    public TicketFindByIdResponse findById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public TicketUpdateResponse update(TicketUpdateRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public TicketDeleteByIdResponse deleteById(Long id) {
        return null;
    }
}
