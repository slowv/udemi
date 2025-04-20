package com.slowv.udemi.service.impl;

import com.slowv.udemi.repository.CourseRepository;
import com.slowv.udemi.repository.LessonRepository;
import com.slowv.udemi.repository.search.CourseSearchRepository;
import com.slowv.udemi.service.CourseService;
import com.slowv.udemi.service.dto.CourseRecord;
import com.slowv.udemi.service.dto.LessonRecord;
import com.slowv.udemi.service.dto.request.CourseSearchRequest;
import com.slowv.udemi.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    // REPOSITORY
    private final CourseSearchRepository courseSearchRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    // MAPPER
    private final CourseMapper courseMapper;

    @Override
    public CourseRecord create(final CourseRecord course) {
        final var entity = courseMapper.toEntity(course);

        final var lessonIds = course.lessons()
                .stream()
                .map(LessonRecord::id)
                .toList();

        final var lessons = lessonRepository.findAllById(lessonIds);
        entity.setLessons(lessons);

        courseRepository.save(entity);
        courseSearchRepository.index(entity);
        return courseMapper.toDto(entity);
    }

    @Override
    public Page<CourseRecord> search(final CourseSearchRequest request) {
        return courseSearchRepository.search(CriteriaQuery.builder(Criteria.where("name")).build())
                .map(courseMapper::toDto);
    }
}
