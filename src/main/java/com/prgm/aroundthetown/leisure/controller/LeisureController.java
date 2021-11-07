package com.prgm.aroundthetown.leisure.controller;

import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureFindAllByCategoryResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureFindAllByHostResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureResponseDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequestDto;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponseDto;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.leisure.service.LeisureServiceImpl;
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
        return new ResponseEntity<>(leisureService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/leisure/{leisureId}")
    public ResponseEntity<LeisureResponseDto> findLeisureById(@PathVariable final Long leisureId) {
        return new ResponseEntity<>(leisureService.findById(leisureId), HttpStatus.OK);
    }

    @GetMapping("/leisure/leisureCategories/{leisureCategory}")
    public ResponseEntity<List<LeisureFindAllByCategoryResponseDto>> findAllLeisureByLeisureCategory(@PathVariable final LeisureCategory leisureCategory) {
        return new ResponseEntity<>(leisureService.findAllByLeisureCategory(leisureCategory), HttpStatus.OK);
    }

    @GetMapping("/leisure/hosts/{hostId}")
    public ResponseEntity<List<LeisureFindAllByHostResponseDto>> findAllLeisureByHost(@PathVariable final Long hostId) {
        return new ResponseEntity<>(leisureService.findAllByHost(hostId), HttpStatus.OK);
    }

    @PutMapping("/leisure")
    public ResponseEntity<LeisureUpdateResponseDto> updateLeisure(@RequestBody final LeisureUpdateRequestDto request){
        return new ResponseEntity<>(leisureService.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/leisure/{leisureId}")
    public ResponseEntity<LeisureDeleteResponseDto> deleteLeisureById(@PathVariable final Long leisureId) {
        return new ResponseEntity<>(leisureService.deleteById(leisureId), HttpStatus.OK);
    }

}
