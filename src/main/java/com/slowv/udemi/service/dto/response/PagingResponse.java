package com.slowv.udemi.service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingResponse<T> {
    PageableData paging;
    List<T> contents = new ArrayList<>();

    public static <T> PagingResponse<T> from(Page<T> page) {
        final var response = new PagingResponse<T>();
        response.setContents(page.getContent());
        response.setPaging(PageableData.from(page));
        return response;
    }
}
