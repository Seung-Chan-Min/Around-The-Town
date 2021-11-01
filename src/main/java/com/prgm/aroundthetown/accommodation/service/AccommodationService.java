package com.prgm.aroundthetown.accommodation.service;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;

public interface AccommodationService {

    AccommodationCreateResponseDto save(AccommodationCreateRequestDto accommodationCreateDto);
}