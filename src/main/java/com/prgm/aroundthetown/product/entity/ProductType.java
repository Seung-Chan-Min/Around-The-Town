package com.prgm.aroundthetown.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProductType {
    ACCOMMODATION("ACCOMMODATION"),
    LEISURE("LEISURE");

    @JsonValue
    private final String typeName;

    ProductType(final String typeName) {
        this.typeName = typeName;
    }
}
