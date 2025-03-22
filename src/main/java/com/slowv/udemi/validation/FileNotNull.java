package com.slowv.udemi.validation;


import com.slowv.udemi.validation.handler.FileNotNullHandler;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileNotNullHandler.class) // Liên kết với lớp Validator
@Target({ ElementType.FIELD, ElementType.METHOD }) // Áp dụng cho trường hoặc phương thức
@Retention(RetentionPolicy.RUNTIME) // Annotation tồn tại trong runtime
public @interface FileNotNull {
    String message() default "File not must be null!"; // Thông báo lỗi mặc định
    Class<?>[] groups() default {}; // Dùng cho nhóm validation
    Class<? extends Payload>[] payload() default {}; // Dùng để mang thông tin metadata
}
