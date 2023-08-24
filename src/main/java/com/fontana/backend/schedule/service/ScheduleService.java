package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.dto.ScheduleCardDto;
import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.entity.ScheduleCycleDays;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }

    public void updateSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Integer id) {
        return scheduleRepository.getReferenceById(id);
    }

    public boolean isCycle(Schedule schedule) {
        return !schedule.getCycleDays().isEmpty();
    }

    public Set<Integer> getDaysHavingSchedulesInMonth(Integer year, Integer month) {
        LocalDate date = LocalDate.of(year, month, 1);

        Long monthStart = Timestamp.valueOf(date.atStartOfDay()).getTime();

        date = date.plusMonths(1);

        Long monthEnd = Timestamp.valueOf(date.atStartOfDay()).getTime();

        List<Schedule> schedules = scheduleRepository.getSchedulesInTimestampRange(monthStart, monthEnd);

        Set<Integer> days = new HashSet<>();

        for (Schedule schedule : schedules) {
            if (isCycle(schedule)) {
                for (ScheduleCycleDays cycleDays : schedule.getCycleDays()) {
                    LocalDate start = LocalDate.of(schedule.getStartTimestamp().getYear() + 1900, schedule.getStartTimestamp().getMonth() + 1, schedule.getStartTimestamp().getDate());

                    LocalDate end;
                    if (schedule.getEndTimestamp() == null) {
                        end = date;
                    } else {
                        end = LocalDate.of(schedule.getEndTimestamp().getYear() + 1900, schedule.getEndTimestamp().getMonth() + 1, schedule.getEndTimestamp().getDate()).plusDays(1);
                    }
                    switch (cycleDays) {
                        case MON:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.MONDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                        case TUE:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.TUESDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                        case WED:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.WEDNESDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                        case THU:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.THURSDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                        case FRI:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.FRIDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                        case SAT:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.SATURDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                        case SUN:
                            for (LocalDate localDate : getCycleDays(start, end, DayOfWeek.SUNDAY)) {
                                days.add(localDate.getDayOfMonth());
                            }
                            break;
                    }
                }
            } else {
                days.add(schedule.getStartTimestamp().getDate());
            }
        }

        return days;
    }

    private List<LocalDate> getCycleDays(LocalDate start, LocalDate end, DayOfWeek dayOfWeek) {
        LocalDate date = start.with(TemporalAdjusters.nextOrSame(dayOfWeek));

        if (!date.isAfter(end)) {
            return date.datesUntil(end, Period.ofWeeks(1)).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<ScheduleCardDto> getSchedulesInDay(Integer year, Integer month, Integer day) {
        List<ScheduleCardDto> schedules = new ArrayList<>();

        //logic

        return schedules;
    }
}
