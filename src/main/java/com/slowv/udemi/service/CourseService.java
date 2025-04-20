package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.CourseRecord;
import com.slowv.udemi.service.dto.request.CourseSearchRequest;
import org.springframework.data.domain.Page;

public interface CourseService {
    CourseRecord create(CourseRecord course);

    Page<CourseRecord> search(CourseSearchRequest request);
}
