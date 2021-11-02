package com.prgm.aroundthetown.product.converter;

import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

    public Location toLocation(final LocationDto dto) {
        return Location.builder()
                .howToVisit(dto.getHowToVisit())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .content(dto.getContent())
                .build();
    }

    public LocationDto toResponse(final Location location) {
        return LocationDto.builder()
                .howToVisit(location.getHowToVisit())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .content(location.getContent())
                .build();
    }
}
