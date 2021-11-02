package com.prgm.aroundthetown.accommodation.contoller;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.service.AccommodationService;
import com.prgm.aroundthetown.product.vo.Region;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("/hosts/accommodations")
    public ResponseEntity<AccommodationCreateResponseDto> saveAccommodation(
            @RequestBody AccommodationCreateRequestDto accommodationCreateDto
    ) {
        AccommodationCreateResponseDto response = accommodationService.save(accommodationCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/hosts/{hostId}/accommodations")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationsByHostId(
            @PathVariable Long hostId
    ){
        List<AccommodationResponseDto> accommodationResponses = accommodationService.geAccommodationByHostId(hostId);
        return ResponseEntity.ok(accommodationResponses);
    }

    @GetMapping("/accommodations")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationsByCategoryAndRegion(
            @RequestParam AccommodationCategory category,
            @RequestParam Region region
    ){
        List<AccommodationResponseDto> response = accommodationService.getAccommodationsByCategoryAndRegion(category, region);
        return ResponseEntity.ok(response);
    }

}