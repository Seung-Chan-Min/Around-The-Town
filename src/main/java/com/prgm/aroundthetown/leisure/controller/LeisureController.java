package com.prgm.aroundthetown.leisure.controller;

import com.prgm.aroundthetown.common.response.ApiResponse;
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

    @PostMapping("/leisure")
    public ResponseEntity<ApiResponse<Long>> createLeisure(@RequestBody final LeisureCreateRequestDto request) {
        return ResponseEntity.ok(ApiResponse.created(leisureService.create(request)));
    }

    @GetMapping("/leisure/{leisureId}")
    public ResponseEntity<ApiResponse<LeisureResponseDto>> findLeisureById(@PathVariable final Long leisureId) {
        return ResponseEntity.ok(ApiResponse.ok(leisureService.findById(leisureId)));
    }

    @GetMapping("/leisure/leisureCategories/{leisureCategory}")
    public ResponseEntity<ApiResponse<List<LeisureFindAllByCategoryResponseDto>>> findAllLeisureByLeisureCategory(@PathVariable final LeisureCategory leisureCategory) {
        return ResponseEntity.ok(ApiResponse.ok(leisureService.findAllByLeisureCategory(leisureCategory)));
    }

    @GetMapping("/leisure/hosts/{hostId}")
    public ResponseEntity<ApiResponse<List<LeisureFindAllByHostResponseDto>>> findAllLeisureByHost(@PathVariable final Long hostId) {
        return ResponseEntity.ok(ApiResponse.ok(leisureService.findAllByHost(hostId)));
    }

    @PutMapping("/leisure")
    public ResponseEntity<ApiResponse<LeisureUpdateResponseDto>> updateLeisure(@RequestBody final LeisureUpdateRequestDto request){
        return ResponseEntity.ok(ApiResponse.ok(leisureService.update(request)));
    }

    @DeleteMapping("/leisure/{leisureId}")
    public ResponseEntity<ApiResponse<LeisureDeleteResponseDto>> deleteLeisureById(@PathVariable final Long leisureId) {
        return ResponseEntity.ok(ApiResponse.ok(leisureService.deleteById(leisureId)));
    }

}
