package com.prgm.aroundthetown.product.converter;

import com.prgm.aroundthetown.accommodation.dto.AccommodationDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.leisure.dto.LeisureDto;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductDeleteResponseDto;
import com.prgm.aroundthetown.product.dto.ProductDto;
import com.prgm.aroundthetown.product.dto.ProductResponseDto;
import com.prgm.aroundthetown.product.dto.ProductUpdateResponseDto;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final LocationConverter locationConverter;

    public ProductDto accommodationToDto(final Accommodation accommodation) {
        final ProductDto productDto = toProductDtoIncludeLocation(accommodation);
        final AccommodationDto accommodationDto = AccommodationDto.builder()
            .accommodationName(accommodation.getAccommodationName())
            .accommodationCategory(accommodation.getAccommodationCategory())
            .accommodationNotice(accommodation.getAccommodationNotice())
            .optionNotice(accommodation.getOptionNotice())
            .guide(accommodation.getGuide())
            .build();
        productDto.setAccommodationDto(accommodationDto);

        final LeisureDto leisureDto = LeisureDto.builder().build();
        productDto.setLeisureDto(leisureDto);

        return productDto;
    }

    public ProductDto leisureToDto(final Leisure leisure) {
        final ProductDto productDto = toProductDtoIncludeLocation(leisure);

        final LeisureDto leisureDto = LeisureDto.builder()
            .category(leisure.getCategory())
            .leisureInfomation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .build();
        productDto.setLeisureDto(leisureDto);

        final AccommodationDto accommodationDto = AccommodationDto.builder().build();
        productDto.setAccommodationDto(accommodationDto);

        return productDto;
    }

    public ProductDto toProductDtoIncludeLocation(final Product product) {
        final LocationDto locationDto = LocationDto.builder()
            .howToVisit(product.getLocation().getHowToVisit())
            .latitude(product.getLocation().getLatitude())
            .longitude(product.getLocation().getLongitude())
            .content(product.getLocation().getContent())
            .build();
        return ProductDto.builder()
            .productId(product.getProductId())
            .locationDto(locationDto)
            .region(product.getRegion())
            .refundRule(product.getRefundRule())
            .phoneNumber(product.getPhoneNumber())
            .businessRegistrationNumber(product.getBusinessRegistrationNumber())
            .businessAddress(product.getBusinessAddress())
            .businessName(product.getBusinessName())
            .build();
    }

    public ProductResponseDto toResponse(Leisure leisure) {
        return ProductResponseDto.builder()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .region(leisure.getRegion())
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }

    public ProductDeleteResponseDto toDeleteResponse(Leisure leisure) {
        return ProductDeleteResponseDto.builder()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .region(leisure.getRegion())
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }


    public ProductUpdateResponseDto toUpdateResponse(Leisure leisure) {
        return ProductUpdateResponseDto.builder()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .region(leisure.getRegion())
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }
}
