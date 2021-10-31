package com.prgm.aroundthetown.product.service;


import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationCreateRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationCreateResponse;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationDeleteRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationDeleteResponse;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationFindRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationFindResponse;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationUpdateRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationUpdateResponse;

public interface AccommodationService {
    AccommodationCreateResponse createAccommodation(
        AccommodationCreateRequest accommodationCreateRequest, Host host);

    AccommodationFindResponse findAccommodationById(
        AccommodationFindRequest accommodationFindRequest);

    AccommodationUpdateResponse updateAccommodation(
        AccommodationUpdateRequest accommodationUpdateRequest);

    AccommodationDeleteResponse deleteAccommodation(
        AccommodationDeleteRequest accommodationDeleteRequest);
}
