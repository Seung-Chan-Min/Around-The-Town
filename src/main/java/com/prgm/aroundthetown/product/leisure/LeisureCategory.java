package com.prgm.aroundthetown.product.leisure;

import lombok.Getter;

@Getter
public enum LeisureCategory {
    THEMEPARK("테마파크"),
    AMUSEMENTPARK("놀이공원"),
    SKI("스키"),
    SURFING("서핑"),
    AQUARIUM("아쿠아리움"),
    WATERPARK("워터파크");

    private final String leisureName;

    LeisureCategory(final String leisureName) {
        this.leisureName = leisureName;
    }
}
