package com.prgm.aroundthetown.product;

import com.prgm.aroundthetown.product.vo.Region;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.stream.Stream;
//
//@Converter
//public class RegionConverter implements AttributeConverter<Region, String> {
//
//    @Override
//    public String convertToDatabaseColumn(final Region region) {
//        return region.getRegionName();
//    }
//
//    @Override
//    public Region convertToEntityAttribute(final String dbData) {
//        return Stream.of(Region.values())
//                .filter(c -> c.equals(dbData))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
//
//    public List<String> convertToSubRegionList(final Region region) {
//        return region.getSubRegion();
//    }
//
//}
