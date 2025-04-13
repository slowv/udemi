package com.slowv.udemi.repository.specification;

import com.slowv.udemi.entity.FileHistoryEntity;
import com.slowv.udemi.entity.enums.FileUploadStatus;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileHistorySpecification {
    private final List<Specification<FileHistoryEntity>> specifications = new ArrayList<>();

    public static FileHistorySpecification builder() {
        return new FileHistorySpecification();
    }

    public FileHistorySpecification withUUID(String uuid) {
        if (!ObjectUtils.isEmpty(uuid)) {
            specifications.add(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("uuid"), uuid)
            );
        }
        return this;
    }

    public FileHistorySpecification withStatus(final FileUploadStatus status) {
        if (!ObjectUtils.isEmpty(status)) {
            specifications.add(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public Specification<FileHistoryEntity> build() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(specifications.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, query, criteriaBuilder)).toArray(Predicate[]::new));
    }

}
