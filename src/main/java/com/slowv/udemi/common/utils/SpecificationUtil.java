package com.slowv.udemi.common.utils;

import co.elastic.clients.elasticsearch._types.FieldValue;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class SpecificationUtil {

    public String like(final String value) {
        return "%" + value.toUpperCase() + "%";
    }

    public String wildcard(final String value) {
        return "*" + value + "*";
    }

    public List<FieldValue> toFieldValue(List<?> values) {
        return values.stream()
                .map(FieldValue::of)
                .toList();
    }
}
