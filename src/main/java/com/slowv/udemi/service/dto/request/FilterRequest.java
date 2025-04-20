package com.slowv.udemi.service.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class FilterRequest<T> extends FilterDynamicRequest<T> {
    private String search;

    private PagingRequest paging = new PagingRequest();
}
