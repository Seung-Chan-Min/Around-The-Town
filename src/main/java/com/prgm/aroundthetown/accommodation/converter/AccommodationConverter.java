package com.prgm.aroundthetown.accommodation.converter;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccommodationConverter {

    public Product createDtoToEntity(
            final AccommodationCreateRequestDto createDto
            , final Host host
    ) {
        return Accommodation.builder()
                .accommodationName(createDto.getAccommodationName())
                .accommodationNotice(createDto.getAccommodationNotice())
                .optionNotice(createDto.getOptionNotice())
                .guide(createDto.getGuide())
                .accommodationCategory(createDto.getAccommodationCategory())
                .businessName(createDto.getBusinessName())
                .refundRule(createDto.getRefundRule())
                .location(
                        locationDtoToEntity(
                                createDto.getLocation()))
                .phoneNumber(createDto.getPhoneNumber())
                .businessRegistrationNumber(createDto.getBusinessRegistrationNumber())
                .businessAddress(createDto.getBusinessAddress())
                .region(createDto.getRegion())
                .host(host)
                .build();
    }

    public Location locationDtoToEntity(final LocationDto location) {
        return Location.builder()
                .content(location.getContent())
                .howToVisit(location.getHowToVisit())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public AccommodationCreateResponseDto entityToResponseAccommodationCreateDto(
            final Accommodation accommodation
    ) {
        return AccommodationCreateResponseDto.builder()
                .businessName(accommodation.getBusinessName())
                .AccommodationName(accommodation.getAccommodationName())
                .build();
    }

    public List<AccommodationResponseDto> AccommodationEntityToResponseDto(
            final List<Accommodation> accommodations
    ) {
        return accommodations
                .stream()
                .map(accommodation -> AccommodationResponseDto.builder()
                        .accommodationName(accommodation.getAccommodationName())
                        .accommodationCategory(accommodation.getAccommodationCategory())
                        .accommodationNotice(accommodation.getAccommodationNotice())
                        .optionNotice(accommodation.getOptionNotice())
                        .guide(accommodation.getGuide())
                        .build())
                .collect(Collectors.toList());
    }
}
