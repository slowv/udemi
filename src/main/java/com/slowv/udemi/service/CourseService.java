package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto create(CourseDto dto);

    List<CourseDto> getAll();
}
