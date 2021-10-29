package com.prgm.aroundthetown.product.accommodation.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccommodationOptionCategoryConverter implements AttributeConverter<AccommodationOptionCategory, String> {

    @Override
    public String convertToDatabaseColumn(final AccommodationOptionCategory optionCategory) {
        return optionCategory.getOptionDescription();
    }

    @Override
    public AccommodationOptionCategory convertToEntityAttribute(final String dbData) {
        return AccommodationOptionCategory.valueOf(dbData);
    }

}
