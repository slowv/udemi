package com.slowv.udemi.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecificationUtil {

    public String like(final String value) {
        return "%" + value.toUpperCase() + "%";
    }
}
