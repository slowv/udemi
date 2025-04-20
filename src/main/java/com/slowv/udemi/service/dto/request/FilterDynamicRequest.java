package com.slowv.udemi.service.dto.request;

import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author trinh
 * @since 4/20/2025 - 2:41 PM
 */
public abstract class FilterDynamicRequest<T> {
    public abstract Specification<T> specification();

    public CriteriaQuery criteriaQuery() {
        return new CriteriaQuery(new Criteria());
    }
}
