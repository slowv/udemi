package com.slowv.udemi.service.dto.request;


import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import com.slowv.udemi.entity.enums.RegisterType;
import com.slowv.udemi.repository.specification.RegisterCourseSpecification;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterCourseFilterRequest extends FilterRequest<RegisterCourseEntity> {

    Long courseId;

    Long lessonId;

    RegisterLessonStatus status;

    Long studentId;

    Long teacherId;

    String email;

    RegisterType registerType;

    BigDecimal totalAmountFrom;

    BigDecimal totalAmountTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdDate;

    @Override
    public Specification<RegisterCourseEntity> specification() {
        return RegisterCourseSpecification.builder()
                .withEmail(email)
                .withRegisterType(registerType)
                .withTotalAmountBetween(totalAmountFrom, totalAmountTo)
                .withCreatedDateLessThanOrEqual(createdDate)
                .build();
    }
}
