package com.slowv.udemi.repository.specification.custom.impl;

import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.repository.specification.custom.RegisterCourseRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static com.slowv.udemi.repository.specification.RegisterCourseSpecification.FIELD_TOTAL_AMOUNT;
import static com.slowv.udemi.repository.specification.RegisterCourseSpecification.FIELD_TOTAL_PAID_AMOUNT;

public class RegisterCourseRepositoryCustomImpl implements RegisterCourseRepositoryCustom<RegisterCourseEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Long> getIds(final Specification<RegisterCourseEntity> specification) {
        final var criteriaBuilder = em.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(Long.class);
        final var root = query.from(RegisterCourseEntity.class);

        final var predicate = specification.toPredicate(root, query, criteriaBuilder);
        query.where(predicate);

        query.select(root.get("id"));

        return em.createQuery(query)
                .getResultList();
    }

    @Override
    public BigDecimal getTotalAmount(final Specification<RegisterCourseEntity> specification) {
        final var criteriaBuilder = em.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(BigDecimal.class);
        final var root = query.from(RegisterCourseEntity.class);

        final var predicate = specification.toPredicate(root, query, criteriaBuilder);
        query.where(predicate);

        query.select(criteriaBuilder.sum(root.get("totalAmount")));

        return em.createQuery(query)
                .getSingleResult();
    }

    @Override
    public BigDecimal getLackOfRevenue(final Specification<RegisterCourseEntity> specification) {
        final var criteriaBuilder = em.getCriteriaBuilder();
        final var query = criteriaBuilder.createQuery(BigDecimal.class);
        final var root = query.from(RegisterCourseEntity.class);

        final var predicate = specification.toPredicate(root, query, criteriaBuilder);
        query.where(predicate);

        final var diff = criteriaBuilder.diff(
                criteriaBuilder.sum(root.get(FIELD_TOTAL_AMOUNT)),
                criteriaBuilder.sum(root.get(FIELD_TOTAL_PAID_AMOUNT))
        );
        query.select(criteriaBuilder.toBigDecimal(diff));

        return em.createQuery(query)
                .getSingleResult();
    }
}
