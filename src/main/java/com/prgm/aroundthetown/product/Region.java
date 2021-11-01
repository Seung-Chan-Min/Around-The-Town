package com.prgm.aroundthetown.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Region {
    SEOUL("서울", Arrays.asList("강동", "강북", "강서", "강남")),
    GYEONGGI("경기도", Arrays.asList("경기북부", "경기남부")),
    GANGWON("강원도", Arrays.asList("강원북부", "강원남부")),
    CHUNGCHEONG("충청도", Arrays.asList("충청북도", "충청남도")),
    GYEONGSANG("경상도", Arrays.asList("경상북도", "경상남도")),
    JEONLA("전라도", Arrays.asList("전라북도", "전라남도")),
    JAEJU("제주도", Arrays.asList("제주시 서부", "제주시 동부", "서귀포시 서부", "서귀포시 동부"));

    @JsonValue
    private final String regionName;
    private final List<String> subRegion;

    Region(final String regionName, final List<String> subRegion) {
        this.regionName = regionName;
        this.subRegion = subRegion;
    }
}
