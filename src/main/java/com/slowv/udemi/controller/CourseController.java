package com.slowv.udemi.controller;


import com.slowv.udemi.service.dto.RegisterCourseRecord;
import com.slowv.udemi.service.dto.request.ChangeStatusRequest;
import com.slowv.udemi.service.dto.request.RegisterCourseFilterRequest;
import com.slowv.udemi.service.dto.response.PagingResponse;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @PostMapping("/register")
    Response<RegisterCourseRecord> register(final @RequestBody @Valid RegisterCourseRecord request);

    /**
     * Chuyển trạng thái của đơn đăng ký khóa học
     *
     * @param request {@link ChangeStatusRequest}
     * @return No content {@code 204}
     */
    @PostMapping("/change-status")
    Response<Void> changeStatus(final @RequestBody @Valid ChangeStatusRequest request);

    /**
     * Danh sách đăng ký khóa học
     *
     * @param request {@link RegisterCourseFilterRequest}
     * @return Page {@link RegisterCourseRecord}
     */
    @PostMapping
    Response<PagingResponse<RegisterCourseRecord>> getRegisterCourse(final @RequestBody @Valid RegisterCourseFilterRequest request);
}
