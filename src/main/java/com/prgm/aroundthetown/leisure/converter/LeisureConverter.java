package com.prgm.aroundthetown.leisure.converter;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.leisure.dto.*;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.product.converter.LocationConverter;
import com.prgm.aroundthetown.product.converter.ProductConverter;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import com.prgm.aroundthetown.product.dto.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeisureConverter {

    private final LocationConverter locationConverter;
    private final ProductConverter productConverter;

    public Leisure toLeisure(final LeisureCreateRequestDto leisureDto, final Host host) {
        ProductCreateRequestDto prductDto = leisureDto.getProductCreateRequestDto();
        return Leisure.builder()
            .host(host)
            .leisureInformation(leisureDto.getLeisureInformation())
            .usecase(leisureDto.getUsecase())
            .leisureNotice(leisureDto.getLeisureNotice())
            .expirationDate(leisureDto.getExpirationDate())
            .category(leisureDto.getCategory())
            .refundRule(prductDto.getRefundRule())
            .phoneNumber(prductDto.getPhoneNumber())
            .businessRegistrationNumber(prductDto.getBusinessRegistrationNumber())
            .businessAddress(prductDto.getBusinessAddress())
            .businessName(prductDto.getBusinessName())
            .region(prductDto.getRegion())
            .location(locationConverter.toLocation(prductDto.getLocation()))
            .build();
    }

    // Note : 업데이트에 인자로 들어갈 Leisure라서 id필요 X
    public Leisure toLeisure(final LeisureUpdateRequestDto leisureDto) {
        ProductUpdateRequestDto productDto = leisureDto.getProductUpdateRequestDto();
        return Leisure.builder()
            .leisureInformation(leisureDto.getLeisureInformation())
            .usecase(leisureDto.getUsecase())
            .leisureNotice(leisureDto.getLeisureNotice())
            .expirationDate(leisureDto.getExpirationDate())
            .category(leisureDto.getCategory())
            .refundRule(productDto.getRefundRule())
            .phoneNumber(productDto.getPhoneNumber())
            .businessRegistrationNumber(productDto.getBusinessRegistrationNumber())
            .businessAddress(productDto.getBusinessAddress())
            .businessName(productDto.getBusinessName())
            .region(productDto.getRegion())
            .location(locationConverter.toLocation(productDto.getLocation()))
            .build();
    }

    public LeisureResponseDto toResponse(final Leisure leisure) {
        return LeisureResponseDto.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory())
            .productResponseDto(productConverter.toResponse(leisure))
            .build();
    }

    public LeisureFindAllByCategoryResponseDto toFindAllByCategoryResponse(Leisure leisure) {
        return LeisureFindAllByCategoryResponseDto.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory())
            .productResponseDto(productConverter.toResponse(leisure))
            .build();
    }

    public LeisureFindAllByHostResponseDto toFindAllByHostResponse(Leisure leisure) {
        return LeisureFindAllByHostResponseDto.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory())
            .productResponseDto(productConverter.toResponse(leisure))
            .build();
    }

    public LeisureDeleteResponseDto toDeleteResponse(final Leisure leisure) {
        return LeisureDeleteResponseDto.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory())
            .productDeleteResponseDto(productConverter.toDeleteResponse(leisure))
            .build();
    }

    public LeisureUpdateResponseDto toUpdateResponse(final Leisure leisure) {
        return LeisureUpdateResponseDto.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory())
            .productUpdateResponseDto(productConverter.toUpdateResponse(leisure))
            .build();
    }
}
