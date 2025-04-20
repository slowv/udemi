package com.slowv.udemi.repository.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.slowv.udemi.entity.CourseEntity;
import com.slowv.udemi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.stream.Stream;

public interface CourseSearchRepository extends ElasticsearchRepository<CourseEntity, Long>, CourseInternalSearch {
}

interface CourseInternalSearch {
    Stream<CourseEntity> search(String query);

    @Async
    void index(CourseEntity entity);

    @Async
    void deleteFromIndex(CourseEntity entity);

    Page<CourseEntity> search(CriteriaQuery criteria);
}

@RequiredArgsConstructor
class CourseInternalSearchImpl implements CourseInternalSearch {
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final CourseRepository courseRepository;

    @Override
    public Stream<CourseEntity> search(final String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return elasticsearchTemplate.search(nativeQuery, CourseEntity.class).map(SearchHit::getContent).stream();
    }

    @Override
    public void index(final CourseEntity entity) {
        courseRepository.findById(entity.getId())
                .ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndex(final CourseEntity entity) {
        courseRepository.findById(entity.getId())
                .ifPresent(elasticsearchTemplate::delete);
    }

    @Override
    public Page<CourseEntity> search(final CriteriaQuery criteria) {
        final var search = elasticsearchTemplate.search(criteria, CourseEntity.class);
        final var content = search.stream()
                .map(SearchHit::getContent)
                .toList();

        final var pageable = criteria.getPageable();

        return new PageImpl<>(content, pageable, search.getTotalHits());
    }
}
