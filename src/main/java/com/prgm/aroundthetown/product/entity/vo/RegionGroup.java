package com.prgm.aroundthetown.product.entity.vo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum RegionGroup {
    SEOUL("서울", List.of(SeoulType.values())),
    GYEONGGI("경기", Arrays.asList()),
    INCHEON("인천", Arrays.asList()),
    GANGWON("강원", Arrays.asList()),
    JEJU("제주", Arrays.asList()),
    DAEJEON("대전", Arrays.asList()),
    CHUNGCHEONGBUK("충북", Arrays.asList()),
    CHUNGCHEONGNAM("충남", Arrays.asList()),
    BUSAN("부산", Arrays.asList()),
    ULSAN("울산", Arrays.asList()),
    GYEONGSANGBUK("경북", Arrays.asList()),
    GYEONGSANGNAM("경남", Arrays.asList()),
    DAEGU("대구", Arrays.asList()),
    GWANGJU("광주", Arrays.asList()),
    JEOLLABUK("전북", Arrays.asList()),
    JEOLLANAM("전남", Arrays.asList()),
    EMPTY("없음", Collections.emptyList());

    private String title;
    private List<SeoulType> regionList;

    RegionGroup(String title, List<SeoulType> regionList) {
        this.title = title;
        this.regionList = regionList;
    }

    public static RegionGroup findByRegionType(SeoulType regionType){
        return Arrays.stream(RegionGroup.values())
            .filter(regionGroup -> regionGroup.hasRegionCode(regionType))
            .findAny()
            .orElse(EMPTY);
    }

    public boolean hasRegionCode(SeoulType regionType) {
        return regionList.stream()
            .anyMatch(region -> region == regionType);
    }

    public String getTitle() {
        return title;
    }
}
