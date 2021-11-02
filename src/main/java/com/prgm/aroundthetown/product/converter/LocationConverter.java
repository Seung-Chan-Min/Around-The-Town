package com.prgm.aroundthetown.product.converter;

import com.prgm.aroundthetown.product.dto.LocationRequest;
import com.prgm.aroundthetown.product.dto.LocationResponse;
import com.prgm.aroundthetown.product.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

    public Location toLocation(LocationRequest dto) {
        return Location.builder()
            .howToVisit(dto.getHowToVisit())
            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .content(dto.getContent())
            .build();
    }

    public LocationResponse toResponse(Location location) {
        return LocationResponse.builder()
            .howToVisit(location.getHowToVisit())
            .latitude(location.getLatitude())
            .longitude(location.getLongitude())
            .content(location.getContent())
            .build();
    }
}
