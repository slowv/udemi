package com.slowv.udemi.repository.specification.custom;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public interface RegisterCourseRepositoryCustom<T> {
    List<Long> getIds(Specification<T> specification);

    BigDecimal getTotalAmount(Specification<T> specification);

    BigDecimal getLackOfRevenue(Specification<T> specification);
}
