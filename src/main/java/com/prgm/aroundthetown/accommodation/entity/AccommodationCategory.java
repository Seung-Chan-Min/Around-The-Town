package com.prgm.aroundthetown.accommodation.entity;

import lombok.Getter;

@Getter
public enum AccommodationCategory {
    MOTEL("모텔"),
    HOTEL("호텔"),
    PENTION("펜션"),
    RESORT("리조트/콘도"),
    GUESTHOUSE("게하/한옥");

    private final String categoryName;

    AccommodationCategory(final String categoryName) {
        this.categoryName = categoryName;
    }
}
