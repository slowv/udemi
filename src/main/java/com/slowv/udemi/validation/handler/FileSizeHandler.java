package com.slowv.udemi.validation.handler;

import com.slowv.udemi.validation.FileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeHandler implements ConstraintValidator<FileSize, MultipartFile> {
    private static final long UNIT = 1000000;
    private int min;
    private int max;

    @Override
    public void initialize(final FileSize fileSizeAnnotation) {
        min = fileSizeAnnotation.min();
        max = fileSizeAnnotation.max();
    }

    @Override
    public boolean isValid(final MultipartFile value, final ConstraintValidatorContext context) {
        if (value == null || value.getSize() == 0) {
            return true;
        }
        double sizeMB = 1.0 * value.getSize() / UNIT;
        return sizeMB >= min && sizeMB <= max;
    }
}
