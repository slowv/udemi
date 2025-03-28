package com.slowv.udemi.service.impl;


import com.slowv.udemi.controller.errors.BusinessException;
import com.slowv.udemi.entity.LessonProcessEntity;
import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import com.slowv.udemi.repository.AccountRepository;
import com.slowv.udemi.repository.RegisterCourseRepository;
import com.slowv.udemi.service.RegisterCourseService;
import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.AddLessonRequest;
import com.slowv.udemi.service.dto.request.AssignRegisterCourseRequest;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.GetTotalAmountMonthRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import com.slowv.udemi.service.mapper.LessonMapper;
import com.slowv.udemi.service.mapper.RegisterCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterCourseServiceImpl implements RegisterCourseService {

    // Mapper
    private final LessonMapper lessonMapper;
    private final RegisterCourseMapper registerCourseMapper;

    // Repository
    private final RegisterCourseRepository registerCourseRepository;
    private final AccountRepository accountRepository;

    @Override
    public RegisterCourseRecord register(final RegisterCourseRecord request) {
        final var entity = registerCourseMapper.toEntity(request);
        return registerCourseMapper.toDto(registerCourseRepository.save(entity));
    }

    @Override
    public void changeStatus(final ChangeStatusRequest request) {
        final var registerCourses = registerCourseRepository.findAllById(request.courseIds());
        registerCourses.forEach(registerCourse -> registerCourse.setStatus(request.status()));
        registerCourseRepository.saveAll(registerCourses);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegisterCourseRecord> courses(final RegisterCourseFilterRequest request) {
        return registerCourseRepository.findAll(request.specification(), request.getPaging().pageable())
                .map(registerCourseMapper::toDto);
    }

    @Override
    public List<Long> getIds(final RegisterCourseFilterRequest request) {
        return registerCourseRepository.getIds(request.specification());
    }

    @Override
    public BigDecimal getTotalAmount(final GetTotalAmountMonthRequest request) {
        return Optional.ofNullable(registerCourseRepository.getTotalAmount(request.specification()))
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getLackOfRevenue(@NonNull final GetTotalAmountMonthRequest request) {
        return Optional.ofNullable(registerCourseRepository.getLackOfRevenue(request.specification()))
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void assign(final AssignRegisterCourseRequest request, final Long id) {
        final var registerCourse = registerCourseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("400", "Register course not found"));

        if (!RegisterLessonStatus.WAITING_APPROVAL.equals(registerCourse.getStatus())) {
            throw  new BusinessException("400", "Course is registed!");
        }

        final var student = accountRepository.findById(request.getStudentId())
                .orElseThrow(() -> new BusinessException("400", "Account student not found"));

        final var teacher = accountRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new BusinessException("400", "Account teacher not found"));

        registerCourse.setStudent(student);
        registerCourse.setTeacher(teacher);
        registerCourseRepository.save(registerCourse);
    }

    @Override
    public RegisterCourseRecord addLesson(final List<AddLessonRequest> request, final Long id) {
        final var registerCourse = registerCourseRepository.findById(id).orElseThrow(() -> new BusinessException("400", "Register course not found"));
        final var lessonProcesses = request.stream()
                .map(lessonMapper::toEntity)
                .map(lesson -> {
                    final var lessonProcess = new LessonProcessEntity();
                    lessonProcess.setLesson(lesson);
                    lessonProcess.setRegisterCourse(registerCourse);
                    return lessonProcess;
                })
                .toList();

        registerCourse.setLessonProcesses(lessonProcesses);
        return registerCourseMapper.toDto(registerCourse);
    }
}
