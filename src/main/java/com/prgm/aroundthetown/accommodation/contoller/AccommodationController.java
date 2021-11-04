package com.prgm.aroundthetown.accommodation.contoller;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationDeleteDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.service.AccommodationService;
import com.prgm.aroundthetown.product.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("/hosts/accommodations")
    public ResponseEntity<AccommodationCreateResponseDto> saveAccommodation(
            @RequestBody final AccommodationCreateRequestDto accommodationCreateDto
    ) {
        final AccommodationCreateResponseDto response = accommodationService.save(accommodationCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/accommodation-all")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodations() {
        final List<AccommodationResponseDto> accommodationResponses = accommodationService.getAccommodations();
        return ResponseEntity.ok(accommodationResponses);
    }

    // TODO: hostId를 url 에 노출시키면 안될것같은데.. Login 을 하면 hostId를 url로 받지 않고, host 자체를 인증해서 쓸수있을것같음
    @GetMapping("/hosts/{hostId}/accommodations")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationsByHostId(
            @PathVariable final Long hostId
    ) {
        final List<AccommodationResponseDto> accommodationResponses = accommodationService.geAccommodationByHostId(hostId);
        return ResponseEntity.ok(accommodationResponses);
    }

    @GetMapping("/accommodations")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationsByCategoryAndRegion(
            @RequestParam final AccommodationCategory category,
            @RequestParam final Region region
    ) {
        final List<AccommodationResponseDto> response = accommodationService.getAccommodationsByCategoryAndRegion(category, region);
        return ResponseEntity.ok(response);
    }

    //TODO: hostId 를 노출시키면 안될것 같습니다. => 로그인, 인증을 배운후에 고치기
    @DeleteMapping("/hosts/{hostId}/accommodations/{accommodationId}")
    public ResponseEntity<AccommodationDeleteDto> deleteAccommodation(
            @PathVariable final Long hostId,
            @PathVariable final Long accommodationId
    ) {
        final AccommodationDeleteDto deletedAccommodationName = accommodationService.deleteByAccommodationId(hostId, accommodationId);
        return ResponseEntity.ok(deletedAccommodationName);
    }


}