package com.prgm.aroundthetown.product.accommodation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccommodationOptionCategory {
    SPA("스파/욕조"),
    PARKING("주차가능"),
    WIFI("와이파이 지원"),
    NETFLIX("넷플릭스 지원"),
    BREAKFAST("조식 지원"),
    NOTEBOOK("노트북 대여 가능");

    @JsonValue
    private final String optionDescription;

    AccommodationOptionCategory(final String optionDescription) {
        this.optionDescription = optionDescription;
    }
}
