package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link com.slowv.udemi.entity.CourseEntity}
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto implements Serializable {
    Long id;
    String name;
    String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Long> categoryIds;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<CategoryDto> categories = new ArrayList<>();
}