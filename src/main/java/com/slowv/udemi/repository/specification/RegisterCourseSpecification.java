package com.slowv.udemi.repository.specification;

import com.slowv.udemi.common.utils.SpecificationUtil;
import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.entity.enums.RegisterType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegisterCourseSpecification {

    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_REGISTER_TYPE = "registerType";
    private static final String FIELD_TOTAL_AMOUNT = "totalAmount";
    private static final String FIELD_CREATED_DATE = "createdDate";

    private final List<Specification<RegisterCourseEntity>> specifications = new ArrayList<>();

    public static RegisterCourseSpecification builder() {
        return new RegisterCourseSpecification();
    }

    public RegisterCourseSpecification withEmail(final String email) {
        if (!ObjectUtils.isEmpty(email)) {
            specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.like(
                    criteriaBuilder.upper(root.get(FIELD_EMAIL)),
                    SpecificationUtil.like(email))
            );
        }
        return this;
    }

    public RegisterCourseSpecification withRegisterType(final RegisterType registerType) {
        if (!ObjectUtils.isEmpty(registerType)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get(FIELD_REGISTER_TYPE), registerType)
            );
        }
        return this;
    }

    public RegisterCourseSpecification withTotalAmountBetween(
            final BigDecimal from,
            final BigDecimal to
    ) {
        if (!ObjectUtils.isEmpty(from) && !ObjectUtils.isEmpty(to)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.between(root.get(FIELD_TOTAL_AMOUNT), from, to)
            );
        }
        return this;
    }

    public RegisterCourseSpecification withCreatedDateLessThanOrEqual(final LocalDateTime createdDate) {
        if (!ObjectUtils.isEmpty(createdDate)) {
            specifications.add(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get(FIELD_CREATED_DATE), createdDate)
            );
        }
        return this;
    }

    public Specification<RegisterCourseEntity> build() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(specifications.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }
}
