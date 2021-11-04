package com.prgm.aroundthetown.product.converter;

import com.prgm.aroundthetown.accommodation.converter.AccommodationConverter;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.leisure.converter.LeisureConverter;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductDto;
import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.product.entity.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final AccommodationConverter accommodationConverter;
    private final LeisureConverter leisureConverter;

    public ProductDto toDto(final Product product) {
        final ProductDto productDto = ProductDto.builder()
                .productId(product.getProductId())
                .locationDto(LocationDto.builder()
                        .howToVisit(product.getLocation().getHowToVisit())
                        .latitude(product.getLocation().getLatitude())
                        .longitude(product.getLocation().getLongitude())
                        .content(product.getLocation().getContent())
                        .build())
                .region(product.getRegion())
                .refundRule(product.getRefundRule())
                .phoneNumber(product.getPhoneNumber())
                .businessRegistrationNumber(product.getBusinessRegistrationNumber())
                .businessAddress(product.getBusinessAddress())
                .businessName(product.getBusinessName())
                .build();

        if (product.getProductType().equals(ProductType.ACCOMMODATION)) {
            productDto.setAccommodationDto(accommodationConverter.toDto((Accommodation) product));
            return productDto;
        } else {
            productDto.setLeisureDto(leisureConverter.toDto((Leisure) product));
            return productDto;
        }
    }
}
