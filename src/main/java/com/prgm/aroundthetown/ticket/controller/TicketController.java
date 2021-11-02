package com.prgm.aroundthetown.ticket.controller;

import com.prgm.aroundthetown.ticket.dto.TicketCreateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketFindByIdResponse;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequest;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponse;
import com.prgm.aroundthetown.ticket.service.TicketService;
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

    // Todo : 중복코드 리펙토링
    @ExceptionHandler // Todo : Test
    private ResponseEntity<String> exceptionHandle(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Todo : 중복코드 리펙토링
    @ExceptionHandler(NotFoundException.class)  // Todo : Test
    private ResponseEntity<String> notFoundHandle(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ticket")
    public ResponseEntity<Long> createTicket(@RequestBody final TicketCreateRequest request) {
        Long ticketCreateResponse = ticketService.create(request);
        return new ResponseEntity<>(ticketCreateResponse, HttpStatus.CREATED); // Todo : OK , CREATED ?
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketFindByIdResponse> findTicketById(@PathVariable final Long ticketId) {
        TicketFindByIdResponse ticketFindByIdResponse = ticketService.findById(ticketId);
        return new ResponseEntity<>(ticketFindByIdResponse, HttpStatus.OK);
    }

    @PutMapping("/ticket")
    public ResponseEntity<TicketUpdateResponse> updateTicket(@RequestBody final TicketUpdateRequest request){
        TicketUpdateResponse updated = ticketService.update(request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketDeleteByIdResponse> deleteTicketById(@PathVariable final Long ticketId) {
        TicketDeleteByIdResponse ticketDeleteByIdResponse = ticketService.deleteById(ticketId);
        return new ResponseEntity<>(ticketDeleteByIdResponse, HttpStatus.OK);
    }

}
