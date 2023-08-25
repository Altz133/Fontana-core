package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.dto.ScheduleCardDto;
import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.entity.ScheduleCycleDays;
import com.fontana.backend.schedule.mapper.ScheduleDateMapper;
import com.fontana.backend.schedule.mapper.ScheduleMapper;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleDateService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleDateMapper scheduleDateMapper;

    public Set<Integer> getDaysHavingSchedulesInMonth(Integer year, Integer month) {
        LocalDate date = LocalDate.of(year, month, 1);

        Long monthStart = Timestamp.valueOf(date.atStartOfDay()).getTime();

        date = date.plusMonths(1);

        Long monthEnd = Timestamp.valueOf(date.atStartOfDay()).getTime();

        List<Schedule> schedules = scheduleRepository.getSchedulesInTimestampRange(monthStart, monthEnd);

        Set<Integer> days = new HashSet<>();

        for (Schedule schedule : schedules) {
            if (scheduleService.isCycle(schedule)) {
                for (ScheduleCycleDays cycleDays : schedule.getCycleDays()) {
                    LocalDate start = LocalDate.of(schedule.getStartTimestamp().getYear() + 1900, schedule.getStartTimestamp().getMonth() + 1, schedule.getStartTimestamp().getDate());

                    LocalDate end;

                    if (schedule.getEndTimestamp() == null) {
                        end = date;
                    } else {
                        end = LocalDate.of(schedule.getEndTimestamp().getYear() + 1900, schedule.getEndTimestamp().getMonth() + 1, schedule.getEndTimestamp().getDate()).plusDays(1);
                    }

                    for (LocalDate localDate : getCycleDays(start, end, scheduleDateMapper.map(cycleDays))) {
                        days.add(localDate.getDayOfMonth());
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
        List<ScheduleCardDto> scheduleCardDtos = new ArrayList<>();

        LocalDate date = LocalDate.of(year, month, day);

        Long dayStart = Timestamp.valueOf(date.atStartOfDay()).getTime();

        Long dayEnd = Timestamp.valueOf(date.plusDays(1).atStartOfDay()).getTime();

        List<Schedule> schedules = scheduleRepository.getSchedulesInTimestampRange(dayStart, dayEnd);

        ScheduleCycleDays scheduleCycleDays = scheduleDateMapper.map(date.getDayOfWeek());

        for (Schedule schedule : schedules) {
            if (schedule.isEnabled()) {
                if (scheduleService.isCycle(schedule)) {
                    if (schedule.getCycleDays().contains(scheduleCycleDays)) {
                        scheduleCardDtos.add(scheduleMapper.ScheduleToScheduleCardDto(schedule));
                    }
                } else {
                    scheduleCardDtos.add(scheduleMapper.ScheduleToScheduleCardDto(schedule));
                }
            }
        }

        return scheduleCardDtos;
    }

    public Schedule getNextSchedule() {
        Schedule nextSchedule = null;

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        Long now = Timestamp.valueOf(LocalDateTime.now()).getTime();
        List<Schedule> schedules = scheduleRepository.getEnabledSchedulesInFutureFrom(now);

        Map<Integer, LocalDateTime> dateTimes = new HashMap<>();

        for (Schedule schedule : schedules) {
            LocalTime startTime = schedule.getStartTimestamp().toLocalDateTime().toLocalTime();

            if (scheduleService.isCycle(schedule)) {
                DayOfWeek dayOfWeek = localDate.getDayOfWeek();

                for (int i = 0; i < 7; i++) {
                    ScheduleCycleDays scheduleCycleDay = scheduleDateMapper.map(dayOfWeek);

                    if (schedule.getCycleDays().contains(scheduleCycleDay)) {
                        LocalDateTime nextDateTime = LocalDateTime.of(localDate.plusDays(i), startTime);

                        if (nextDateTime.isAfter(localDateTime)) {
                            if (schedule.getEndTimestamp() == null) {
                                dateTimes.put(schedule.getId(), nextDateTime);
                            } else if (nextDateTime.isBefore(schedule.getEndTimestamp().toLocalDateTime().toLocalDate().plusDays(1).atStartOfDay())) {
                                dateTimes.put(schedule.getId(), nextDateTime);
                            }

                            break;
                        }
                    }

                    dayOfWeek = dayOfWeek.plus(1);
                }
            } else {
                LocalDateTime nextDateTime = LocalDateTime.of(schedule.getStartTimestamp().toLocalDateTime().toLocalDate(), startTime);

                if (nextDateTime.isAfter(localDateTime)) {
                    dateTimes.put(schedule.getId(), nextDateTime);
                }
            }
        }

        if (dateTimes.size() != 0) {
            LocalDateTime first = dateTimes.values().stream().sorted().toList().get(0);

            for (Integer id : dateTimes.keySet()) {
                if (first == dateTimes.get(id)) {
                    for (Schedule schedule : schedules) {
                        if (schedule.getId() == id) {
                            nextSchedule = schedule;

                            break;
                        }
                    }

                    break;
                }
            }
        }

        return nextSchedule;
    }

    public Timestamp getScheduleStartTimestamp(Schedule schedule) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        LocalTime startTime = schedule.getStartTimestamp().toLocalDateTime().toLocalTime();

        if (scheduleService.isCycle(schedule)) {
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();

            for (int i = 0; i < 7; i++) {
                ScheduleCycleDays scheduleCycleDay = scheduleDateMapper.map(dayOfWeek);

                if (schedule.getCycleDays().contains(scheduleCycleDay)) {
                    LocalDateTime nextDateTime = LocalDateTime.of(localDate.plusDays(i), startTime);

                    if (nextDateTime.isAfter(localDateTime)) {
                        if (schedule.getEndTimestamp() == null) {
                            return Timestamp.valueOf(nextDateTime);
                        } else if (nextDateTime.isBefore(schedule.getEndTimestamp().toLocalDateTime().toLocalDate().plusDays(1).atStartOfDay())) {
                            return Timestamp.valueOf(nextDateTime);
                        }

                        break;
                    }
                }

                dayOfWeek = dayOfWeek.plus(1);
            }
        } else {
            LocalDateTime nextDateTime = LocalDateTime.of(schedule.getStartTimestamp().toLocalDateTime().toLocalDate(), startTime);

            if (nextDateTime.isAfter(localDateTime)) {
                return Timestamp.valueOf(nextDateTime);
            }
        }

        return null;
    }
}
