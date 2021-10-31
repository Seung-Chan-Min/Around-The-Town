package com.prgm.aroundthetown.product.leisure.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class LeisureCategoryConverter implements AttributeConverter<LeisureCategory, String> {

    @Override
    public String convertToDatabaseColumn(final LeisureCategory category) {
        return category.getLeisureName();
    }

    @Override
    public LeisureCategory convertToEntityAttribute(final String dbData) {
        return Stream.of(LeisureCategory.values())
                .filter(c -> c.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
