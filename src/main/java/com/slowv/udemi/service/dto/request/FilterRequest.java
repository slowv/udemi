package com.slowv.udemi.service.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class FilterRequest<T> {
    private String search;

    private PagingRequest paging = new PagingRequest();

    public abstract Specification<T> specification();
}
