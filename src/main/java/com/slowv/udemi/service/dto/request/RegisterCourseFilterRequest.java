package com.slowv.udemi.service.dto.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import com.slowv.udemi.entity.enums.RegisterType;
import com.slowv.udemi.repository.specification.RegisterCourseSpecification;
import lombok.AccessLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@ToString
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

    List<RegisterLessonStatus> statuses = new ArrayList<>();

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        final RegisterCourseFilterRequest that = (RegisterCourseFilterRequest) object;
        return Objects.equals(courseId, that.courseId) && Objects.equals(lessonId, that.lessonId) && status == that.status && Objects.equals(studentId, that.studentId) && Objects.equals(teacherId, that.teacherId) && Objects.equals(email, that.email) && registerType == that.registerType && Objects.equals(totalAmountFrom, that.totalAmountFrom) && Objects.equals(totalAmountTo, that.totalAmountTo) && Objects.equals(createdDate, that.createdDate) && Objects.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), courseId, lessonId, status, studentId, teacherId, email, registerType, totalAmountFrom, totalAmountTo, createdDate, statuses);
    }

    @NonNull
    @SneakyThrows
    public String getKeyCache(ObjectMapper objectMapper) {
        return DigestUtils.md5Hex(objectMapper.writeValueAsString(this));
    }

    @Override
    public Specification<RegisterCourseEntity> specification() {
        return RegisterCourseSpecification.builder()
                .withEmail(email)
                .withRegisterType(registerType)
                .withTotalAmountBetween(totalAmountFrom, totalAmountTo)
                .withCreatedDateLessThanOrEqual(createdDate)
                .withSearch(getSearch())
                .withStatusIn(statuses)
                .build();
    }
}
