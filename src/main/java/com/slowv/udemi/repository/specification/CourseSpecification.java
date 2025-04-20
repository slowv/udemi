package com.slowv.udemi.repository.specification;

import com.slowv.udemi.common.utils.SpecificationUtil;
import com.slowv.udemi.entity.CourseEntity;
import com.slowv.udemi.entity.enums.TechSkill;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
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

    private static final String FILED_NAME = "name";
    private static final String FILED_LESSONS = "lessons";
    private static final String FILED_LESSONS_TITLE = "title";
    private static final String FILED_SKILLS = "skills";

    private final List<Specification<CourseEntity>> specifications = new ArrayList<>();
    private final List<Criteria> esCriteria = new ArrayList<>();

    public static CourseSpecification builder() {
        return new CourseSpecification();
    }

    public CourseSpecification withSearch(final String search) {
        if (!ObjectUtils.isEmpty(search)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.or(
                                    criteriaBuilder.like(root.get(FILED_NAME), SpecificationUtil.like(search)),
                                    criteriaBuilder.like(root.get(FILED_LESSONS).get(FILED_LESSONS_TITLE), SpecificationUtil.like(search))
                            )
            );

            esCriteria.add(
                    Criteria.where(FILED_NAME).contains(search)
                            .or(Criteria.where("%s.%s".formatted(FILED_LESSONS, FILED_LESSONS_TITLE)).contains(search))
            );
        }
        return this;
    }

    public CourseSpecification withSkills(final List<TechSkill> skills) {
        if (!ObjectUtils.isEmpty(skills)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            root.get(FILED_SKILLS).in(skills)
            );

            esCriteria.add(
                    Criteria.where(FILED_SKILLS)
                            .in(skills)
            );
        }
        return this;
    }

    @Override
    public CriteriaQuery toQuery(Pageable pageable) {
        final var criteria = this.esCriteria.stream()
                .reduce(Criteria::and)
                .orElse(null);
        return new CriteriaQuery(Objects.requireNonNullElseGet(criteria, Criteria::new))
                .setPageable(pageable);
    }

    @Override
    public Specification<CourseEntity> build() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(specifications.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }
}
