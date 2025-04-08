package com.slowv.udemi.service.dto.request;

import com.slowv.udemi.common.utils.DateUtils;
import com.slowv.udemi.web.rest.errors.BusinessException;
import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.repository.specification.RegisterCourseSpecification;
import com.slowv.udemi.service.dto.request.enums.DateFixed;
import com.slowv.udemi.service.dto.request.enums.PaidStatus;
import com.slowv.udemi.service.dto.request.enums.SearchType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTotalAmountMonthRequest extends FilterRequest<RegisterCourseEntity> {

//    @Size(min = 2, max = 2)
    List<LocalDate> range = new ArrayList<>();

    @NotNull(message = "{searchType.must-not-be-null}")
    SearchType searchType;

    DateFixed dateFixed;

    PaidStatus paidStatus;

    @Override
    public Specification<RegisterCourseEntity> specification() {
        // Tạo Specification builder
        final var builder = RegisterCourseSpecification.builder();

        // Kiểm tra searchType nếu == FIXED
        if (SearchType.FIXED.equals(searchType)) {
            // Kiểm tra xem dateFixed == null hay không nếu null sẽ quăng lỗi
            if (ObjectUtils.isEmpty(dateFixed)) {
                throw new BusinessException("400", "dateFixed must not be null");
            }
            // Gán đè lại giá trị của trường range
            range = DateUtils.getListLocalDateBy(dateFixed);
        } else {
            if (ObjectUtils.isEmpty(this.range) || this.range.size() != 2) {
                throw new BusinessException("400", "size must be equal 2");
            }
        }

        // Nếu chạy vô đoạn code check if bên trên thì range đã được fixed còn không thì là giá trị dưới client gửi lên
        return builder
                .withCreatedDateBetween(this.range)
                .withPaidStatus(this.paidStatus)
                .build();
    }
}
