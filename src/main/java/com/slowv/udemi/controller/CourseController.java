package com.slowv.udemi.controller;

import com.slowv.udemi.service.dto.CourseDto;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/course")
public interface CourseController {

    @PostMapping
    Response<CourseDto> create(@Valid @RequestBody CourseDto dto);

    @GetMapping
    Response<List<CourseDto>> getAll();
}
