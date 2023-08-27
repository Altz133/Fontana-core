package com.fontana.backend.schedule.mapper;

import com.fontana.backend.schedule.entity.ScheduleCycleDays;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class ScheduleDateMapper {
    public ScheduleCycleDays map(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> ScheduleCycleDays.MON;
            case TUESDAY -> ScheduleCycleDays.TUE;
            case WEDNESDAY -> ScheduleCycleDays.WED;
            case THURSDAY -> ScheduleCycleDays.THU;
            case FRIDAY -> ScheduleCycleDays.FRI;
            case SATURDAY -> ScheduleCycleDays.SAT;
            case SUNDAY -> ScheduleCycleDays.SUN;
        };
    }

    public DayOfWeek map(ScheduleCycleDays scheduleCycleDays) {
        return switch (scheduleCycleDays) {
            case MON -> DayOfWeek.MONDAY;
            case TUE -> DayOfWeek.TUESDAY;
            case WED -> DayOfWeek.WEDNESDAY;
            case THU -> DayOfWeek.THURSDAY;
            case FRI -> DayOfWeek.FRIDAY;
            case SAT -> DayOfWeek.SATURDAY;
            case SUN -> DayOfWeek.SUNDAY;
        };
    }
}
