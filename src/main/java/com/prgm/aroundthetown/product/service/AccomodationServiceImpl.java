package com.prgm.aroundthetown.product.service;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.converter.AccommodationConverter;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationCreateRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationCreateResponse;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationDeleteRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationDeleteResponse;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationFindRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationFindResponse;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationUpdateRequest;
import com.prgm.aroundthetown.product.dto.accommodation.AccommodationUpdateResponse;
import com.prgm.aroundthetown.product.entity.Accommodation;
import com.prgm.aroundthetown.product.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccomodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationConverter accommodationConverter;

    @Override
    @Transactional
    public AccommodationCreateResponse createAccommodation(AccommodationCreateRequest dto, Host host) {
        Accommodation accommodation = accommodationConverter.convertToAccommodation(dto);
        accommodation.setHost(host);
        Accommodation savedAccom = accommodationRepository.save(accommodation);
        return accommodationConverter.convertToAccommodationCreateResponse(savedAccom);
    }

    @Override
    public AccommodationFindResponse findAccommodationById(AccommodationFindRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public AccommodationUpdateResponse updateAccommodation(AccommodationUpdateRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public AccommodationDeleteResponse deleteAccommodation(AccommodationDeleteRequest dto) {
        return null;
    }
}
