package com.prgm.aroundthetown.leisure.converter;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureFindByIdResponse;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequest;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.product.converter.LocationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeisureConverter {

    private final LocationConverter locationConverter;

    public Leisure toLeisure(LeisureCreateRequest dto, Host host) {
        return Leisure.builder()
            .host(host)
            .leisureInfomation(dto.getLeisureInfomation())
            .usecase(dto.getUsecase())
            .leisureNotice(dto.getLeisureNotice())
            .expirationDate(dto.getExpirationDate())
            .category(LeisureCategory.valueOf(dto.getCategory()))
            .refundRule(dto.getRefundRule())
            .phoneNumber(dto.getPhoneNumber())
            .businessRegistrationNumber(dto.getBusinessRegistrationNumber())
            .businessAddress(dto.getBusinessAddress())
            .businessName(dto.getBusinessName())
            .location(locationConverter.toLocation(dto.getLocation()))
            .build();
    }

    public LeisureFindByIdResponse toFindByIdResponse(Leisure leisure) {
        return LeisureFindByIdResponse.builder()
            .id(leisure.getProductId())
            .leisureInfomation(leisure.getLeisureInfomation())
            .usecase(leisure.getUsecase())
            .leisureNotice(leisure.getLeisureNotice())
            .expirationDate(leisure.getExpirationDate())
            .category(leisure.getCategory().name()) // Todo : name() or getLeisureName()
            .refundRule(leisure.getRefundRule())
            .phoneNumber(leisure.getPhoneNumber())
            .businessRegistrationNumber(leisure.getBusinessRegistrationNumber())
            .businessAddress(leisure.getBusinessAddress())
            .businessName(leisure.getBusinessName())
            .location(locationConverter.toResponse(leisure.getLocation()))
            .build();
    }

}
