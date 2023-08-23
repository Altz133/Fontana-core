package com.fontana.backend.schedule.service;

import com.fontana.backend.schedule.dto.ScheduleCardDto;
import com.fontana.backend.schedule.entity.Schedule;
import com.fontana.backend.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Set<Integer> getDaysHavingSchedulesInMonth(Integer year, Integer month, Integer timeZoneDiffMin) {
        Long timeDiffInMs = 1000L * 60 * timeZoneDiffMin;

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Long monthStart = calendar.getTimeInMillis();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Long monthEnd = calendar.getTimeInMillis();

        List<Schedule> schedules = scheduleRepository.getSchedulesInTimestampRange(monthStart, monthEnd);

        Set<Integer> days = new HashSet<>();

        //logic

        return days;
    }

    public List<ScheduleCardDto> getSchedulesInDay(Integer year, Integer month, Integer day, Integer timeZoneDiffMin) {
        List<ScheduleCardDto> schedules = new ArrayList<>();

        //logic

        return schedules;
    }
}
