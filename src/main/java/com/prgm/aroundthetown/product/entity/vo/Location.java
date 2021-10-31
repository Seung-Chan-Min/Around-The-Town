package com.prgm.aroundthetown.product.entity.vo;

import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Location {
    private String locationGuide;
    private String Content;
    private double latitude; // 위도
    private double longitude; // 경도
    // Todo : Region (embedded)

    public Location(String locationGuide, String content, double latitude, double longitude) {
        if(locationGuide.length() > 250) throw new IllegalArgumentException("Invalid locationGuide length");
        if(content.length() > 250) throw new IllegalArgumentException("Invalid location content length");
        if(latitude < -90.0 || latitude > 90.0) throw new IllegalArgumentException("Invalid location latitude");
        if(longitude < -180.0 || longitude > 180.0) throw new IllegalArgumentException("Invalid location longitude");
        this.locationGuide = locationGuide;
        Content = content;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
