package com.prgm.aroundthetown.product.converter;

import com.prgm.aroundthetown.product.dto.accommodation.AccommodationCreateRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationCreateResponse;
import com.prgm.aroundthetown.product.entity.Accommodation;
import org.springframework.stereotype.Component;

@Component
public class AccommodationConverter {

    public Accommodation convertToAccommodation(AccommodationCreateRequest dto) {
        return Accommodation.builder().build();
    }

    public AccommodationCreateResponse convertToAccommodationCreateResponse(
        Accommodation accommodation) {
        return AccommodationCreateResponse.builder().build();
    }

}
