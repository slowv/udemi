package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import com.slowv.udemi.entity.enums.RegisterType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link RegisterCourseEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
//@Schema(description = "Đối tượng đăng ký khóa học")
public record RegisterCourseRecord(
        Long id,
//        @Schema(description = "Email người đăng ký", example = "slowv@gmail.com")
        String email,
//        @Schema(description = "Số điện thoại người đăng ký", example = "0349555602")
        String phone,
//        @Schema(description = "Tổng học phí", example = "10000")
        BigDecimal totalAmount,
//        @Schema(description = "Tổng học phí đã thanh toán", example = "1000")
        BigDecimal totalPaidAmount,
//        @Schema(description = "Kiểu đăng ký (LESSON | COURSE)", example = "COURSE")
        RegisterType registerType,
//        @Schema(description = "Trạng thái đơn đăng ký khóa học", example = "WAITING_APPROVAL")
        RegisterLessonStatus status,
//        @Schema(description = "Url hình ảnh", example = "http://image.png")
        String images,
//        @Schema(description = "Ghi chú", example = "Tôi muốn học giờ tối")
        String note,
//        @Schema(description = "Học viên")
        AccountRecord student,
//        @Schema(description = "Danh sách bài học trong khóa học")
        List<LessonProcessRecord> lessonProcesses,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdDate
) implements Serializable {
}