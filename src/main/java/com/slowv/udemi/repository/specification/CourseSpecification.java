package com.slowv.udemi.repository.specification;


import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;
import com.slowv.udemi.common.utils.SpecificationUtil;
import com.slowv.udemi.entity.CourseEntity;
import com.slowv.udemi.entity.enums.TechSkill;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author trinh
 * @since 4/20/2025 - 2:21 PM
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CourseSpecification implements EsSpecification, JpaSpecification<CourseEntity> {

    private static final String FIELD_NAME = "name";
    private static final String FIELD_LESSONS = "lessons";
    private static final String FIELD_LESSONS_TITLE = "title";
    private static final String FIELD_SKILLS = "skills";

    private final List<Specification<CourseEntity>> specifications = new ArrayList<>();
    private final List<Query> queries = new ArrayList<>();

    public static CourseSpecification builder() {
        return new CourseSpecification();
    }

    public CourseSpecification withSearch(final String search) {
        if (!ObjectUtils.isEmpty(search)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.or(
                                    criteriaBuilder.like(criteriaBuilder.upper(root.get(FIELD_NAME)), SpecificationUtil.like(search)),
                                    criteriaBuilder.like(criteriaBuilder.upper(root.get(FIELD_LESSONS).get(FIELD_LESSONS_TITLE)), SpecificationUtil.like(search))
                            )
            );

            // Tạo MatchQuery cho lessons.title
            final var wildcardQuery = WildcardQuery.of(m -> m
                    .field("%s.%s".formatted(FIELD_LESSONS, FIELD_LESSONS_TITLE))
                    .wildcard(SpecificationUtil.wildcard(search))
            );

            // Bọc trong NestedQuery
            final var nestedQuery = Query.of(q ->
                    q.nested(
                            NestedQuery.of(n ->
                                    n.path(FIELD_LESSONS)
                                            .query(Query.of(mq -> mq.wildcard(wildcardQuery)))
                                            .scoreMode(ChildScoreMode.None)
                            )
                    )
            );

            queries.add(nestedQuery);
        }
        return this;
    }

    public CourseSpecification withSkills(final List<TechSkill> skills) {
        if (!ObjectUtils.isEmpty(skills)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            root.get(FIELD_SKILLS).in(skills)
            );

            queries.add(
                    Query.of(q ->
                            q.terms(t ->
                                    t.field(FIELD_SKILLS)
                                            .terms(terms -> terms.value(SpecificationUtil.toFieldValue(skills)))
                            )
                    )
            );
        }
        return this;
    }


    @Override
    public Specification<CourseEntity> build() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(specifications.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }

    @Override
    public org.springframework.data.elasticsearch.core.query.Query toQuery(final Pageable pageable) {
        final var boolQuery = Query.of(q -> q.bool(BoolQuery.of(b -> b.must(queries))));
        return NativeQuery.builder()
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
    }
}
