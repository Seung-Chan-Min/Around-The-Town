package com.prgm.aroundthetown.ticket.controller;

import com.prgm.aroundthetown.ticket.service.TicketService;
import com.prgm.aroundthetown.ticket.service.TicketServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    private final TicketService ticketService;

    // Note : service가 추가될 경우를 고려하여, @RequiredArgsConstructor 사용하지 않음
    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }


}
