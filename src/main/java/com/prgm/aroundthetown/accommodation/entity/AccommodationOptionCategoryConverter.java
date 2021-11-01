package com.prgm.aroundthetown.accommodation.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class AccommodationOptionCategoryConverter implements AttributeConverter<AccommodationOptionCategory, String> {

    @Override
    public String convertToDatabaseColumn(final AccommodationOptionCategory optionCategory) {
        return optionCategory.getOptionDescription();
    }

    @Override
    public AccommodationOptionCategory convertToEntityAttribute(final String dbData) {
        return Stream.of(AccommodationOptionCategory.values())
                .filter(c -> c.equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
