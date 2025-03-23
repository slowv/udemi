package com.slowv.udemi.service.mapper.custom.handler;

import com.slowv.udemi.entity.CategoryEntity;
import com.slowv.udemi.repository.CategoryRepository;
import com.slowv.udemi.service.mapper.custom.GetCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomMapperService {

    private final CategoryRepository categoryRepository;

    @GetCategory
    public List<CategoryEntity> getCategories(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }
}
