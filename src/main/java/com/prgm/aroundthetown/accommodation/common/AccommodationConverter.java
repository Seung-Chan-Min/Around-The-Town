package com.prgm.aroundthetown.accommodation.common;


import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateResponseDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class AccommodationConverter {

    public Product createDtoToEntity(
            AccommodationCreateRequestDto createDto
            , Host host
    ) {
        return Accommodation.builder()
                .accommodationName(createDto.getAccommodationName())
                .accommodationNotice(createDto.getAccommodationNotice())
                .optionNotice(createDto.getOptionNotice())
                .guide(createDto.getGuide())
                .accommodationCategory(createDto.getAccommodationCategory())
                .businessName(createDto.getBusinessName())
                .refundRule(createDto.getRefundRule())
                .location(createDto.getLocation())
                .phoneNumber(createDto.getPhoneNumber())
                .businessRegistrationNumber(createDto.getBusinessRegistrationNumber())
                .businessAddress(createDto.getBusinessAddress())
                .region(createDto.getRegion())
                .host(host)
                .build();
    }

    public AccommodationCreateResponseDto entityToResponseAccommodationCreateDto(
            Accommodation accommodation
    ) {
        return AccommodationCreateResponseDto.builder()
                .businessName(accommodation.getBusinessName())
                .AccommodationName(accommodation.getAccommodationName())
                .build();
    }

}
