package com.prgm.aroundthetown.ticket.service;

import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.ticket.converter.TicketConverter;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketFindByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponse;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import com.prgm.aroundthetown.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {
    private final LeisureRepository leisureRepository;
    private final TicketRepository ticketRepository;
    private final TicketConverter ticketConverter;

    @Override
    @Transactional
    public Long create(TicketCreateRequest dto) {
        Leisure leisure = leisureRepository.findById(dto.getLeisureId())
            .orElseThrow(() -> new NotFoundException("Leisure is not found."));
        Ticket ticket = ticketConverter.toTicket(dto, leisure);
        leisure.addTicket(ticket);
        return ticketRepository.save(ticket).getId();
    }

    @Override
    public TicketFindByIdResponse findById(Long id) {
        return ticketRepository.findById(id)
            .map(ticketConverter::toFindByIdResponse)
            .orElseThrow(() -> new NotFoundException("Ticket is not found."));
    }

    @Override
    @Transactional
    public TicketUpdateResponse update(TicketUpdateRequest dto) {
        Ticket ticket = ticketRepository.findById(dto.getId())
            .orElseThrow(() -> new NotFoundException("Ticket is not found."));
        return ticketConverter.toUpdateResponse(ticket.update(dto.getTicketName(), dto.getPrice()));
    }

    @Override
    @Transactional
    public TicketDeleteByIdResponse deleteById(Long id) {
        TicketDeleteByIdResponse ticketDeleteByIdResponse = ticketRepository.findById(id)
            .map(ticketConverter::toDeleteByIdResponse)
            .orElseThrow(() -> new NotFoundException("Ticket is not found."));
        ticketRepository.deleteById(id);
        return ticketDeleteByIdResponse;
    }
}
