package com.slowv.udemi.common.utils;

import com.slowv.udemi.service.dto.request.enums.DateFixed;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@UtilityClass
public class DateUtils {

    public List<LocalDate> getListLocalDateBy(DateFixed dateFixed) {
        final var now = LocalDate.now();
        return switch (dateFixed) {
            case WEEK -> List.of(
                    now.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)),
                    now.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))
            );
            case MONTH ->
                    List.of(now.with(TemporalAdjusters.firstDayOfMonth()), now.with(TemporalAdjusters.lastDayOfMonth()));

            case YEAR ->
                    List.of(now.with(TemporalAdjusters.firstDayOfYear()), now.with(TemporalAdjusters.lastDayOfYear()));
        };
    }
}
