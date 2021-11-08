package com.prgm.aroundthetown.ticket.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
import com.prgm.aroundthetown.ticket.dto.TicketCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketFindAllByLeisureResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponseDto;
import com.prgm.aroundthetown.ticket.service.TicketService;
import java.util.List;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/tickets")
    public ResponseEntity<ApiResponse<Long>> createTicket(@RequestBody TicketCreateRequestDto request) {
        return ResponseEntity.ok(ApiResponse.created(ticketService.create(request)));
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<ApiResponse<TicketResponseDto>> findTicketById(@PathVariable final Long ticketId) {
        return ResponseEntity.ok(ApiResponse.ok(ticketService.findById(ticketId)));
    }

    @GetMapping("/tickets/leisure/{leisureId}")
    public ResponseEntity<ApiResponse<List<TicketFindAllByLeisureResponseDto>>> findAllTicketByLeisure(@PathVariable final Long leisureId) {
        return ResponseEntity.ok(ApiResponse.ok(ticketService.findAllByLeisure(leisureId)));
    }

    @PutMapping("/tickets")
    public ResponseEntity<ApiResponse<TicketUpdateResponseDto>> updateTicket(@RequestBody final TicketUpdateRequestDto request){
        return ResponseEntity.ok(ApiResponse.ok(ticketService.update(request)));
    }

    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<ApiResponse<TicketDeleteResponseDto>> deleteTicketById(@PathVariable final Long ticketId) {
        return ResponseEntity.ok(ApiResponse.ok(ticketService.deleteById(ticketId)));
    }

}
