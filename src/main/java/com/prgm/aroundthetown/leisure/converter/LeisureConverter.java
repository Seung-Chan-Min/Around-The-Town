package com.prgm.aroundthetown.leisure.converter;

import com.prgm.aroundthetown.leisure.dto.LeisureCreateRequest;
import com.prgm.aroundthetown.leisure.dto.LeisureUpdateRequest;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import org.springframework.stereotype.Component;

@Component
public class LeisureConverter {

    public Leisure toLeisure(LeisureCreateRequest dto) {
        return Leisure.builder()
            .leisureInfomation(dto.getLeisureInfomation())
            .usecase(dto.getUsecase())
            .leisureNotice(dto.getLeisureNotice())
            .expirationDate(dto.getExpirationDate())
            .category(LeisureCategory.valueOf(dto.getCategory()))
            .build();
    }

    public Leisure toLeisure(LeisureUpdateRequest dto){
        return Leisure.builder()
            .build();
    }

}
