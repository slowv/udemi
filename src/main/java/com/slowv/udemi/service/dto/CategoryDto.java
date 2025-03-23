package com.slowv.udemi.service.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.slowv.udemi.entity.CategoryEntity}
 */
@Value
public class CategoryDto implements Serializable {
    Long id;
    String name;
    String description;
}