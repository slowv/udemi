package com.slowv.udemi.controller.impl;

import com.slowv.udemi.controller.CourseController;
import com.slowv.udemi.service.RegisterCourseService;
import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.GetTotalAmountMonthRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import com.slowv.udemi.service.dto.response.PagingResponse;
import com.slowv.udemi.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController {

    private final RegisterCourseService registerCourseService;

    @Override
    public Response<RegisterCourseRecord> register(final RegisterCourseRecord request) {
        return Response.created(registerCourseService.register(request));
    }

    @Override
    public Response<Void> changeStatus(final ChangeStatusRequest request) {
        return null;
    }

    @Override
    public Response<PagingResponse<RegisterCourseRecord>> getRegisterCourse(final RegisterCourseFilterRequest request) {
        return Response.ok(PagingResponse.from(registerCourseService.courses(request)));
    }

    @Override
    public Response<List<Long>> getIds(final RegisterCourseFilterRequest request) {
        return Response.ok(registerCourseService.getIds(request));
    }

    @Override
    public Response<BigDecimal> getTotalAmount(final GetTotalAmountMonthRequest request) {
        return Response.ok(registerCourseService.getTotalAmount(request));
    }

    @Override
    public Response<BigDecimal> getLackOfRevenue(final GetTotalAmountMonthRequest request) {
        return Response.ok(registerCourseService.getLackOfRevenue(request));
    }
}
