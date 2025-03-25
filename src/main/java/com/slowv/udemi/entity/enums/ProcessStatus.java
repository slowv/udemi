package com.slowv.udemi.entity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ProcessStatus {
    NOT_STARTED("Chưa bắt đầu"),
    IN_PROGRESS("Đang học"),
    FINISHED("Đã hoàn thành");
    String name;
}
