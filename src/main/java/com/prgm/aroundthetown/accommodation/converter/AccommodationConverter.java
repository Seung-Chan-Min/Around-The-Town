package com.prgm.aroundthetown.accommodation.converter;

import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationDeleteDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationResponseDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.product.entity.ProductType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccommodationConverter {
    public AccommodationDto toDto(final Accommodation accommodation) {
        return AccommodationDto.builder()
                .accommodationName(accommodation.getAccommodationName())
                .accommodationCategory(accommodation.getAccommodationCategory())
                .accommodationNotice(accommodation.getAccommodationNotice())
                .optionNotice(accommodation.getOptionNotice())
                .guide(accommodation.getGuide())
                .build();
    }

    public Product createDtoToEntity(
            final AccommodationCreateRequestDto createDto,
            final Host host
    ) {
        final ProductCreateRequestDto productDto = createDto.getProductDto();
        final Accommodation accommodation = Accommodation.builder()
                .accommodationName(createDto.getAccommodationName())
                .accommodationNotice(createDto.getAccommodationNotice())
                .optionNotice(createDto.getOptionNotice())
                .guide(createDto.getGuide())
                .accommodationCategory(createDto.getAccommodationCategory())
                .businessName(productDto.getBusinessName())
                .refundRule(productDto.getRefundRule())
                .location(
                        locationDtoToEntity(
                                productDto.getLocation()))
                .phoneNumber(productDto.getPhoneNumber())
                .businessRegistrationNumber(productDto.getBusinessRegistrationNumber())
                .businessAddress(productDto.getBusinessAddress())
                .region(productDto.getRegion())
                .productType(ProductType.ACCOMMODATION)
                .host(host)
                .build();
        host.addProduct(accommodation);
        return accommodation;
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

    public AccommodationDeleteDto entityToAccommodationDeleteDto(final Accommodation accommodation) {
        return AccommodationDeleteDto.builder()
                .accommodationName(accommodation.getAccommodationName())
                .build();
    }
}
