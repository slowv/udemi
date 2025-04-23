package com.slowv.udemi.service.dto.request;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author trinh
 * @since 4/20/2025 - 2:41 PM
 */
public abstract class FilterDynamicRequest<T> {
    public abstract Specification<T> specification();

    public Query criteriaQuery() {
        return new NativeQuery(co.elastic.clients.elasticsearch._types.query_dsl.Query.of(q -> q));
    }
}
