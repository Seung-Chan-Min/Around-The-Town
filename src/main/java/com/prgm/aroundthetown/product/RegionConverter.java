package com.prgm.aroundthetown.product;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class RegionConverter implements AttributeConverter<Region, String> {

    @Override
    public String convertToDatabaseColumn(final Region region) {
        return region.getRegionName();
    }

    @Override
    public Region convertToEntityAttribute(final String dbData) {
        return Region.valueOf(dbData);
    }

    public List<String> convertToSubRegionList(final Region region) {
        return region.getSubRegion();
    }

}
