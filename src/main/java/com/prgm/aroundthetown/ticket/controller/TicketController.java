package com.prgm.aroundthetown.ticket.controller;

import com.prgm.aroundthetown.ticket.dto.TicketCreateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketDeleteResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketResponseDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateRequestDto;
import com.prgm.aroundthetown.ticket.dto.TicketUpdateResponseDto;
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
    public ResponseEntity<Long> createTicket(@RequestBody final TicketCreateRequestDto request) {
        Long ticketCreateResponse = ticketService.create(request);
        return new ResponseEntity<>(ticketCreateResponse, HttpStatus.CREATED); // Todo : OK , CREATED ?
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketResponseDto> findTicketById(@PathVariable final Long ticketId) {
        TicketResponseDto ticketResponseDto = ticketService.findById(ticketId);
        return new ResponseEntity<>(ticketResponseDto, HttpStatus.OK);
    }

    @PutMapping("/ticket")
    public ResponseEntity<TicketUpdateResponseDto> updateTicket(@RequestBody final TicketUpdateRequestDto request){
        TicketUpdateResponseDto updated = ticketService.update(request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketDeleteResponseDto> deleteTicketById(@PathVariable final Long ticketId) {
        TicketDeleteResponseDto ticketDeleteResponseDto = ticketService.deleteById(ticketId);
        return new ResponseEntity<>(ticketDeleteResponseDto, HttpStatus.OK);
    }

}
