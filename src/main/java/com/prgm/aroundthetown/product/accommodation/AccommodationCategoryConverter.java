package com.prgm.aroundthetown.product.accommodation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccommodationCategoryConverter implements AttributeConverter<AccommodationCategory, String> {

    @Override
    public String convertToDatabaseColumn(final AccommodationCategory accommodationCategory) {
        return accommodationCategory.getCategoryName();
    }

    @Override
    public AccommodationCategory convertToEntityAttribute(final String dbData) {
        return AccommodationCategory.valueOf(dbData);
    }

}
