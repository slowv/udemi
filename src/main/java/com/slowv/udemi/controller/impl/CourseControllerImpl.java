package com.slowv.udemi.controller.impl;

import com.slowv.udemi.controller.CourseController;
import com.slowv.udemi.service.CourseService;
import com.slowv.udemi.service.dto.CourseDto;
import com.slowv.udemi.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController {

    private final CourseService courseService;

    @Override
    public Response<CourseDto> create(final CourseDto dto) {
        return Response.created(courseService.create(dto));
    }

    @Override
    public Response<List<CourseDto>> getAll() {
        return Response.ok(courseService.getAll());
    }
}
