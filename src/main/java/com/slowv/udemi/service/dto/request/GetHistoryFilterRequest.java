package com.slowv.udemi.service.dto.request;

import com.slowv.udemi.entity.FileHistoryEntity;
import com.slowv.udemi.entity.enums.FileUploadStatus;
import com.slowv.udemi.repository.specification.FileHistorySpecification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetHistoryFilterRequest extends FilterRequest<FileHistoryEntity> {

    private String uuid;
    private final FileUploadStatus status;

    @Override
    public Specification<FileHistoryEntity> specification() {
        return FileHistorySpecification
                .builder()
                .withUUID(uuid)
                .withStatus(status)
                .build();
    }
}
