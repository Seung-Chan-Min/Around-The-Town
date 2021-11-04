package com.prgm.aroundthetown.accommodation.service;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationDeleteDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.Region;

import java.util.List;

public interface AccommodationService {

    AccommodationCreateResponseDto save(AccommodationCreateRequestDto accommodationCreateDto);

    List<AccommodationResponseDto> getAccommodations();

    List<AccommodationResponseDto> geAccommodationByHostId(Long hostId);

    List<AccommodationResponseDto> getAccommodationsByCategoryAndRegion(AccommodationCategory category, Region region);

    AccommodationDeleteDto deleteByAccommodationId(final Long hostId, Long accommodationId);
}