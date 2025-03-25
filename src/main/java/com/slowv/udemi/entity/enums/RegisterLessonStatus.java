package com.slowv.udemi.entity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RegisterLessonStatus {
    WAITING_APPROVAL("Chờ duyệt"),
    REJECTED("Từ chối"),
    REGISTERED("Đã đăng ký");

    String name;
}
