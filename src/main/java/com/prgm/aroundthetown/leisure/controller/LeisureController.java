package com.prgm.aroundthetown.leisure.controller;

import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponseDto;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
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
public class LeisureController {
    private final LeisureServiceImpl leisureService;

    // Todo : 중복코드 리펙토링
    @ExceptionHandler  // Todo : Test
    private ResponseEntity<String> exceptionHandle(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Todo : 중복코드 리펙토링
    @ExceptionHandler(NotFoundException.class)  // Todo : Test
    private ResponseEntity<String> notFoundHandle(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/leisure")
    public ResponseEntity<Long> createLeisure(@RequestBody final LeisureCreateRequestDto request) {
        Long created = leisureService.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED); // Todo : OK , CREATED ?
    }

    @GetMapping("/leisure/{leisureId}")
    public ResponseEntity<LeisureResponseDto> findLeisureById(@PathVariable final Long leisureId) {
        LeisureResponseDto found = leisureService.findById(leisureId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/leisure")
    public ResponseEntity<LeisureUpdateResponseDto> updateLeisure(@RequestBody final LeisureUpdateRequestDto request){
        LeisureUpdateResponseDto updated = leisureService.update(request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/leisure/{leisureId}")
    public ResponseEntity<LeisureDeleteResponseDto> deleteLeisureById(@PathVariable final Long leisureId) {
        LeisureDeleteResponseDto deleted = leisureService.deleteById(leisureId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    
}
