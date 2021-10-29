package com.prgm.aroundthetown.product.leisure;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LeisureCategoryConverter implements AttributeConverter<LeisureCategory, String> {

    @Override
    public String convertToDatabaseColumn(final LeisureCategory category) {
        return category.getLeisureName();
    }

    @Override
    public LeisureCategory convertToEntityAttribute(final String dbData) {
        return LeisureCategory.valueOf(dbData);
    }

}
