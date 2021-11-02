package com.prgm.aroundthetown.leisure.converter;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureDeleteByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureFindByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateResponse;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.product.converter.LocationConverter;
import com.prgm.aroundthetown.product.entity.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeisureConverter {

    private final LocationConverter locationConverter;

    public Leisure toLeisure(LeisureCreateRequest dto, Host host) {
        return Leisure.builder()
            .host(host)
            .leisureInformation(dto.getLeisureInformation())
            .usecase(dto.getUsecase())
            .leisureNotice(dto.getLeisureNotice())
            .expirationDate(dto.getExpirationDate())
            .category(LeisureCategory.valueOf(dto.getCategory()))
            .refundRule(dto.getRefundRule())
            .phoneNumber(dto.getPhoneNumber())
            .businessRegistrationNumber(dto.getBusinessRegistrationNumber())
            .businessAddress(dto.getBusinessAddress())
            .businessName(dto.getBusinessName())
            .region(Region.valueOf(dto.getRegion()))
            .location(locationConverter.toLocation(dto.getLocation()))
            .build();
    }

    // Note : 업데이트에 인자로 들어갈 Leisure라서 id필요 X
    public Leisure toLeisure(LeisureUpdateRequest dto) {
        return Leisure.builder()
            .leisureInformation(dto.getLeisureInformation())
            .usecase(dto.getUsecase())
            .leisureNotice(dto.getLeisureNotice())
            .expirationDate(dto.getExpirationDate())
            .category(LeisureCategory.valueOf(dto.getCategory()))
            .refundRule(dto.getRefundRule())
            .phoneNumber(dto.getPhoneNumber())
            .businessRegistrationNumber(dto.getBusinessRegistrationNumber())
            .businessAddress(dto.getBusinessAddress())
            .businessName(dto.getBusinessName())
            .region(Region.valueOf(dto.getRegion()))
            .location(locationConverter.toLocation(dto.getLocation()))
            .build();
    }

    public LeisureFindByIdResponse toFindByIdResponse(Leisure leisure) {
        return LeisureFindByIdResponse.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory().name()) // Todo : name() or getLeisureName()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .region(leisure.getRegion().name()) // Todo : name() or getRegionName()
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }

    public LeisureDeleteByIdResponse toDeleteByIdResponse(Leisure leisure) {
        return LeisureDeleteByIdResponse.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory().name()) // Todo : name() or getLeisureName()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .region(leisure.getRegion().name()) // Todo : name() or getRegionName()
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }

    public LeisureUpdateResponse toUpdateResponse(Leisure leisure) {
        return LeisureUpdateResponse.builder()
            .id(leisure.getProductId())
            .leisureInformation(leisure.getLeisureInformation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory().name()) // Todo : name() or getLeisureName()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .region(leisure.getRegion().name()) // Todo : name() or getRegionName()
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }
}
