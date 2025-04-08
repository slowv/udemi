package com.slowv.udemi.web.rest.impl;

import com.slowv.udemi.web.rest.CourseController;
import com.slowv.udemi.entity.AccountEntity;
import com.slowv.udemi.integration.mail.MailService;
import com.slowv.udemi.service.RegisterCourseService;
import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.AddLessonRequest;
import com.slowv.udemi.service.dto.request.AssignRegisterCourseRequest;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.GetTotalAmountMonthRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import com.slowv.udemi.service.dto.response.PagingResponse;
import com.slowv.udemi.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController {

    private final RegisterCourseService registerCourseService;
    private final MessageSource messageSource;
    private final MailService mailService;

    @Override
    public Response<RegisterCourseRecord> register(final RegisterCourseRecord request) {
        return Response.created(registerCourseService.register(request));
    }

    @Override
    public Response<Void> changeStatus(final ChangeStatusRequest request) {
        registerCourseService.changeStatus(request);
        return Response.noContent();
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

    @Override
    public Response<String> assignTeacher(final AssignRegisterCourseRequest request, final Long id) {
        registerCourseService.assign(request, id);
        return Response.ok("Assign Success!!!");
    }

    @Override
    public Response<RegisterCourseRecord> addLesson(final List<AddLessonRequest> request, final Long id) {
        return Response.ok(registerCourseService.addLesson(request, id));
    }

    @Override
    public Response<String> testI18n(
            final String language,
            final GetTotalAmountMonthRequest request
    ) {
        Locale.setDefault(new Locale("vi"));
        final var account = new AccountEntity();
        account.setEmail("SlowV@Gmail.com");
        mailService.sendEmailActiveAccount("trinhhoangnam602@gmail.com", "Active account!", account, "123456");
        return Response.ok(messageSource.getMessage("hello", null, new Locale(language)));
    }
}
