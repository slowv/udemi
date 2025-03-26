package com.slowv.udemi.service;


import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.GetTotalAmountMonthRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface RegisterCourseService {

    RegisterCourseRecord register(RegisterCourseRecord request);

    void changeStatus(ChangeStatusRequest request);

    Page<RegisterCourseRecord> courses(RegisterCourseFilterRequest request);

    List<Long> getIds(RegisterCourseFilterRequest request);

    BigDecimal getTotalAmount(GetTotalAmountMonthRequest request);

    BigDecimal getLackOfRevenue(GetTotalAmountMonthRequest request);
}
