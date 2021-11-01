package com.prgm.aroundthetown.product.leisure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LeisureCategory {
    THEMEPARK("테마파크"),
    AMUSEMENTPARK("놀이공원"),
    SKI("스키"),
    SURFING("서핑"),
    AQUARIUM("아쿠아리움"),
    WATERPARK("워터파크");

    @JsonValue
    private final String leisureName;

    LeisureCategory(final String leisureName) {
        this.leisureName = leisureName;
    }
}
