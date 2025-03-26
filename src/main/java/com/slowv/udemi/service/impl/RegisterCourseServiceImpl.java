package com.slowv.udemi.service.impl;


import com.slowv.udemi.repository.RegisterCourseRepository;
import com.slowv.udemi.service.RegisterCourseService;
import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.GetTotalAmountMonthRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import com.slowv.udemi.service.mapper.RegisterCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterCourseServiceImpl implements RegisterCourseService {

    private final RegisterCourseMapper registerCourseMapper;
    private final RegisterCourseRepository registerCourseRepository;

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
    public BigDecimal getLackOfRevenue(final GetTotalAmountMonthRequest request) {
        return Optional.ofNullable(registerCourseRepository.getLackOfRevenue(request.specification()))
                .orElse(BigDecimal.ZERO);
    }
}
