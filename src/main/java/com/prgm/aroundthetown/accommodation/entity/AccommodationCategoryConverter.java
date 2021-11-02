package com.prgm.aroundthetown.accommodation.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

//@Converter
//public class AccommodationCategoryConverter implements AttributeConverter<AccommodationCategory, String> {
//
//    @Override
//    public String convertToDatabaseColumn(final AccommodationCategory accommodationCategory) {
//        return accommodationCategory.getCategoryName();
//    }
//
//    @Override
//    public AccommodationCategory convertToEntityAttribute(final String dbData) {
//        return Stream.of(AccommodationCategory.values())
//                .filter(c -> c.equals(dbData))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
//
//}
