package com.slowv.udemi.repository.specification;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface JpaSpecification<T> {
    Specification<T> build();
}
