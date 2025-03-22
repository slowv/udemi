package com.slowv.udemi.validation.handler;

import com.slowv.udemi.validation.FileNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileNotNullHandler implements ConstraintValidator<FileNotNull, MultipartFile> {


    /**
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return true là pass còn false là failure
     */
    @Override
    public boolean isValid(final MultipartFile value, final ConstraintValidatorContext context) {
        return !ObjectUtils.isEmpty(value) && !value.isEmpty();
    }
}
