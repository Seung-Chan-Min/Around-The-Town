package com.prgm.aroundthetown.accommodation.service;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.vo.Region;

import java.util.List;

public interface AccommodationService {

    AccommodationCreateResponseDto save(AccommodationCreateRequestDto accommodationCreateDto);

    List<AccommodationResponseDto> geAccommodationByHostId(Long hostId);

    List<AccommodationResponseDto> getAccommodationsByCategoryAndRegion(AccommodationCategory category, Region region);
}