package com.slowv.udemi.service.dto.request;

import com.slowv.udemi.entity.CourseEntity;
import com.slowv.udemi.entity.enums.TechSkill;
import com.slowv.udemi.repository.specification.CourseSpecification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author trinh
 * @since 4/20/2025 - 2:09 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseSearchRequest extends FilterRequest<CourseEntity> {

    private List<TechSkill> skills = new ArrayList<>();

    @Override
    public Specification<CourseEntity> specification() {
        return this.builder()
                .withSearch(this.getSearch())
                .build();
    }

    @Override
    public CriteriaQuery criteriaQuery() {
        return builder()
                .toQuery(this.getPaging().pageable());
    }

    private CourseSpecification builder() {
        return CourseSpecification.builder()
                .withSearch(this.getSearch())
                .withSkills(this.skills);
    }
}
