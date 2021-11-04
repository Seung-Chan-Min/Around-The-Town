package com.prgm.aroundthetown.ticket.controller;

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

    @PostMapping("/tickets")
    public ResponseEntity<Long> createTicket(@RequestBody final TicketCreateRequestDto request) {
        return new ResponseEntity<>(ticketService.create(request), HttpStatus.CREATED); // Todo : OK , CREATED ?
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketResponseDto> findTicketById(@PathVariable final Long ticketId) {
        return new ResponseEntity<>(ticketService.findById(ticketId), HttpStatus.OK);
    }

    @GetMapping("/tickets/leisure/{leisureId}")
    public ResponseEntity<List<TicketFindAllByLeisureResponseDto>> findAllTicketByLeisure(@PathVariable final Long leisureId) {
        return new ResponseEntity<>(ticketService.findAllByLeisure(leisureId), HttpStatus.OK);
    }

    @PutMapping("/tickets")
    public ResponseEntity<TicketUpdateResponseDto> updateTicket(@RequestBody final TicketUpdateRequestDto request){
        return new ResponseEntity<>(ticketService.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDeleteResponseDto> deleteTicketById(@PathVariable final Long ticketId) {
        return new ResponseEntity<>(ticketService.deleteById(ticketId), HttpStatus.OK);
    }

}
