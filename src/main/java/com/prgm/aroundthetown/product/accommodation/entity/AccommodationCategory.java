package com.prgm.aroundthetown.product.accommodation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccommodationCategory {
    MOTEL("모텔"),
    HOTEL("호텔"),
    PENTION("펜션"),
    RESORT("리조트/콘도"),
    GUESTHOUSE("게하/한옥");

    @JsonValue
    private final String categoryName;

    AccommodationCategory(final String categoryName) {
        this.categoryName = categoryName;
    }
}
