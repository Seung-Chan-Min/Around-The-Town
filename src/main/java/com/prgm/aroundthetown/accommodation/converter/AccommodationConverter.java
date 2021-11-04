package com.prgm.aroundthetown.accommodation.converter;

import com.prgm.aroundthetown.accommodation.dto.*;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationOption;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import com.prgm.aroundthetown.product.entity.Product;
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

    //TODO: create update 받아오는 dto가 동일하고, 내용도 duplicated 되는데 공통코드를 빼서 쓰고싶을때 해결방법?
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
                .host(host)
                .build();
        createDto.getAccommodationOptions()
                .forEach(accommodationOptionDto -> accommodation
                        .addOption(AccommodationOption.builder()
                                .accommodation(accommodation)
                                .option(accommodationOptionDto.getAccommodationOptionCategory())
                                .build()));
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

    public AccommodationUpdateResponseDto entityToResponseAccommodationUpdateDto(
            final Accommodation accommodation
    ) {
        return AccommodationUpdateResponseDto.builder()
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

    public void update(final AccommodationUpdateRequestDto updateRequestDto, final Product accommodation) {
        accommodation.update(
                updateRequestDto.getProductDto().getRefundRule(),
                updateRequestDto.getProductDto().getPhoneNumber(),
                updateRequestDto.getProductDto().getBusinessRegistrationNumber(),
                updateRequestDto.getProductDto().getBusinessAddress(),
                updateRequestDto.getProductDto().getBusinessName(),
                updateRequestDto.getProductDto().getRegion(),
                getLocation(updateRequestDto.getProductDto().getLocation())
        );
    }

    public Location getLocation(final LocationDto locationDto) {
        return Location.builder()
                .content(locationDto.getContent())
                .howToVisit(locationDto.getHowToVisit())
                .longitude(locationDto.getLongitude())
                .latitude(locationDto.getLatitude())
                .build();
    }
}
