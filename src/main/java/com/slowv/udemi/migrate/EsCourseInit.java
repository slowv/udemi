package com.slowv.udemi.migrate;

import com.slowv.udemi.repository.CourseRepository;
import com.slowv.udemi.repository.search.CourseSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EsCourseInit implements CommandLineRunner {

    private final CourseSearchRepository courseSearchRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (courseSearchRepository.count() == 0) {
            final var all = courseRepository.findAll();
            courseSearchRepository.saveAll(all);
        }
    }
}
