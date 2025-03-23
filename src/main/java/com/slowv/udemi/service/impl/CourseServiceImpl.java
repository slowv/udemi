package com.slowv.udemi.service.impl;

import com.slowv.udemi.repository.CourseRepository;
import com.slowv.udemi.service.CourseService;
import com.slowv.udemi.service.dto.CourseDto;
import com.slowv.udemi.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseDto create(final CourseDto dto) {
        return courseMapper.toDto(courseRepository.save(courseMapper.toEntity(dto)));
    }

    @Override
    public List<CourseDto> getAll() {
        return courseMapper.toDto(courseRepository.findAll());
    }
}
