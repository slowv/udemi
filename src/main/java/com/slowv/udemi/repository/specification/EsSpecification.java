package com.slowv.udemi.repository.specification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Query;

@FunctionalInterface
public interface EsSpecification {
    Query toQuery(Pageable pageable);
}
