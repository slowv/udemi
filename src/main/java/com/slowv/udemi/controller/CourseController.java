package com.slowv.udemi.controller;


import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.AddLessonRequest;
import com.slowv.udemi.service.dto.request.AssignRegisterCourseRequest;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.GetTotalAmountMonthRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import com.slowv.udemi.service.dto.response.PagingResponse;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller Khóa học
 */
@RequestMapping("/api/courses")
public interface CourseController {

    /**
     * Đăng ký khóa học
     *
     * @param request {@link RegisterCourseRecord} thông tin đăng ký khóa học
     * @return Message thông báo đăng ký thành công!
     */
    @Secured({"STUDENT", "GUEST"})
    @PostMapping("/register")
    Response<RegisterCourseRecord> register(final @RequestBody @Valid RegisterCourseRecord request);

    /**
     * Chuyển trạng thái của đơn đăng ký khóa học
     *
     * @param request {@link ChangeStatusRequest}
     * @return No content {@code 204}
     */
    @Secured({"TEACHER"})
    @PostMapping("/change-status")
    Response<Void> changeStatus(final @RequestBody @Valid ChangeStatusRequest request);

    /**
     * Danh sách đăng ký khóa học
     *
     * @param request {@link RegisterCourseFilterRequest}
     * @return Page {@link RegisterCourseRecord}
     */
//    @Secured({"TEACHER", "STUDENT", "GUEST", "ADMIN"})
//    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_GUEST', 'ROLE_ADMIN')")
    @PostMapping
    Response<PagingResponse<RegisterCourseRecord>> getRegisterCourse(final @RequestBody @Valid RegisterCourseFilterRequest request);

    @PostMapping("/ids")
    Response<List<Long>> getIds(final @RequestBody @Valid RegisterCourseFilterRequest request);

    @PostMapping("/get-total-amount")
    Response<BigDecimal> getTotalAmount(final @RequestBody @Valid GetTotalAmountMonthRequest request);

    @PostMapping("/get-lack-of-revenue")
    Response<BigDecimal> getLackOfRevenue(final @RequestBody @Valid GetTotalAmountMonthRequest request);

    @PutMapping("/{id}/assign")
    Response<String> assignTeacher(
            final @RequestBody @Valid AssignRegisterCourseRequest request,
            @PathVariable final Long id
    );

    @PostMapping("/{id}/add-lesson")
    Response<RegisterCourseRecord> addLesson(
            final @RequestBody @Valid List<AddLessonRequest> request,
            @PathVariable final Long id
    );

    @PostMapping("/test-i18n")
    Response<String> testI18n(
            @RequestHeader("Accept-Language") String language,
            @RequestBody @Valid GetTotalAmountMonthRequest request
    );

}
