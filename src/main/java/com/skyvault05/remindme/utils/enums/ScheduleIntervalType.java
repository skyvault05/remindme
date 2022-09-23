package com.skyvault05.remindme.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleIntervalType {
    ONCE("INTERVALTYPE_ONCE", "한번"),
    WEEKLY("INTERVALTYPE_WEEKLY", "주간"),
    MONTHLY("INTERVALTYPE_MONTHLY", "월간"),
    ANNUAL("INTERVALTYPE_ANNUAL", "연간");

    private final String key;
    private final String title;
}
