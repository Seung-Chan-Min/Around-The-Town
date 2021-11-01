package com.prgm.aroundthetown.accommodation.dto;

import com.prgm.aroundthetown.product.dto.LocationDTO;
import com.prgm.aroundthetown.product.vo.Region;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.product.vo.Location;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AccommodationCreateRequestDto {
    private Long hostId;

    private String refundRule;
    private LocationDTO location;
    private String phoneNumber;
    private String businessRegistrationNumber;
    private String businessAddress;
    private String businessName;
    private Region region;
    private String accommodationName;
    private String accommodationNotice;
    private String optionNotice;
    private String guide;
    private AccommodationCategory accommodationCategory;

}
