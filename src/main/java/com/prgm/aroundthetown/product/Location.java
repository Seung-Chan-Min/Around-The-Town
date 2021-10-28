package com.prgm.aroundthetown.product;

import javax.persistence.Embeddable;

@Embeddable
public class Location {
    private String howToVisit;
    private Double latitude;
    private Double longitude;
    private String content;
}
