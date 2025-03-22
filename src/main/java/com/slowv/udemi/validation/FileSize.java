package com.slowv.udemi.validation;

import com.slowv.udemi.validation.handler.FileSizeHandler;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileSizeHandler.class) // Liên kết với lớp Validator
@Target({ ElementType.FIELD, ElementType.METHOD }) // Áp dụng cho trường hoặc phương thức
@Retention(RetentionPolicy.RUNTIME) // Annotation tồn tại trong runtime
public @interface FileSize {
    String message() default "File not must be null!"; // Thông báo lỗi mặc định
    Class<?>[] groups() default {}; // Dùng cho nhóm validation
    Class<? extends Payload>[] payload() default {}; // Dùng để mang thông tin metadata

    // MB
    int min() default 0;
    // MB
    int max();
}
