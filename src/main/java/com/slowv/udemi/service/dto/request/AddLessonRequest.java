package com.slowv.udemi.service.dto.request;

import com.slowv.udemi.entity.enums.CostType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddLessonRequest {
    @NotBlank
    String title;
    BigDecimal price = BigDecimal.ZERO;
    Integer day = 0;
    String description;
    CostType costType = CostType.COURSE;
}