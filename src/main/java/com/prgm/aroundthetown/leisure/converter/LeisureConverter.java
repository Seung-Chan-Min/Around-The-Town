package com.prgm.aroundthetown.leisure.converter;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.leisure.dto.*;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.product.converter.LocationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeisureConverter {

    private final LocationConverter locationConverter;

    public Leisure toLeisure(final LeisureCreateRequest dto, final Host host) {
        return Leisure.builder()
                .host(host)
                .leisureInformation(dto.getLeisureInformation())
                .usecase(dto.getUsecase())
                .leisureNotice(dto.getLeisureNotice())
                .expirationDate(dto.getExpirationDate())
                .category(dto.getCategory())
                .refundRule(dto.getRefundRule())
                .phoneNumber(dto.getPhoneNumber())
                .businessRegistrationNumber(dto.getBusinessRegistrationNumber())
                .businessAddress(dto.getBusinessAddress())
                .businessName(dto.getBusinessName())
                .region(dto.getRegion())
                .location(locationConverter.toLocation(dto.getLocation()))
                .build();
    }

    // Note : 업데이트에 인자로 들어갈 Leisure라서 id필요 X
    public Leisure toLeisure(final LeisureUpdateRequest dto) {
        return Leisure.builder()
                .leisureInformation(dto.getLeisureInformation())
                .usecase(dto.getUsecase())
                .leisureNotice(dto.getLeisureNotice())
                .expirationDate(dto.getExpirationDate())
                .category(dto.getCategory())
                .refundRule(dto.getRefundRule())
                .phoneNumber(dto.getPhoneNumber())
                .businessRegistrationNumber(dto.getBusinessRegistrationNumber())
                .businessAddress(dto.getBusinessAddress())
                .businessName(dto.getBusinessName())
                .region(dto.getRegion())
                .location(locationConverter.toLocation(dto.getLocation()))
                .build();
    }

    public LeisureFindByIdResponse toFindByIdResponse(final Leisure leisure) {
        return LeisureFindByIdResponse.builder()
                .id(leisure.getProductId())
                .leisureInformation(leisure.getLeisureInformation())
                .usecase(leisure.getUsecase())
                .leisureNotice(leisure.getLeisureNotice())
                .expirationDate(leisure.getExpirationDate())
                .category(leisure.getCategory())
                .refundRule(leisure.getRefundRule())
                .phoneNumber(leisure.getPhoneNumber())
                .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
                .businessAddress(leisure.getBusinessAddress())
                .businessName(leisure.getBusinessName())
                .region(leisure.getRegion())
                .location(locationConverter.toResponse(leisure.getLocation()))
                .build();
    }

    public LeisureDeleteByIdResponse toDeleteByIdResponse(final Leisure leisure) {
        return LeisureDeleteByIdResponse.builder()
                .id(leisure.getProductId())
                .leisureInformation(leisure.getLeisureInformation())
                .usecase(leisure.getUsecase())
                .leisureNotice(leisure.getLeisureNotice())
                .expirationDate(leisure.getExpirationDate())
                .category(leisure.getCategory())
                .refundRule(leisure.getRefundRule())
                .phoneNumber(leisure.getPhoneNumber())
                .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
                .businessAddress(leisure.getBusinessAddress())
                .businessName(leisure.getBusinessName())
                .region(leisure.getRegion())
                .location(locationConverter.toResponse(leisure.getLocation()))
                .build();
    }

    public LeisureUpdateResponse toUpdateResponse(final Leisure leisure) {
        return LeisureUpdateResponse.builder()
                .id(leisure.getProductId())
                .leisureInformation(leisure.getLeisureInformation())
                .usecase(leisure.getUsecase())
                .leisureNotice(leisure.getLeisureNotice())
                .expirationDate(leisure.getExpirationDate())
                .category(leisure.getCategory())
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