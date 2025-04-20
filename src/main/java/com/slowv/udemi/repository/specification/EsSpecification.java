package com.slowv.udemi.repository.specification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

@FunctionalInterface
public interface EsSpecification {
    CriteriaQuery toQuery(Pageable pageable);
}
